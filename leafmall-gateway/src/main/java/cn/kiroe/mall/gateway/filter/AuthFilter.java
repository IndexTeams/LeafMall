package cn.kiroe.mall.gateway.filter;

import cn.hutool.core.text.AntPathMatcher;
import cn.hutool.core.util.StrUtil;
import cn.kiroe.mall.common.constant.ResultCodeEnum;
import cn.kiroe.mall.common.result.Result;
import cn.kiroe.mall.common.util.IpUtil;
import cn.kiroe.mall.user.consts.UserConstants;
import cn.kiroe.mall.user.dto.UserLoginInfoDTO;
import com.alibaba.fastjson.JSONObject;
import org.redisson.api.RBucket;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpCookie;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;

/**
 * 全局过滤器
 * 对需要登录的请求进行拦截
 * 如果没登录进行拦截
 *
 * @Author Kiro
 * @Date 2024/01/29 11:44
 **/
@Component
public class AuthFilter implements GlobalFilter {
    private final AntPathMatcher antPathMatcher = new AntPathMatcher();
    @Value("${authUrls.url}")
    String authUrl; // /*/auth/**
    @Value("${innerApiUrl.url}")
    String innerApiUrl;
    @Autowired
    RedissonClient redissonClient;

    private static String getValueFromCookieOrHeader(ServerHttpRequest request, String key) {
        String headerValue = request.getHeaders().getFirst(key);
        if (StrUtil.isNotBlank(headerValue)) {
            return headerValue;
        }
        HttpCookie cookieValue = request.getCookies().getFirst(key);
        if (cookieValue != null) {
            return cookieValue.getValue();
        }
        return null;
    }

    @Override
    public Mono<Void> filter(final ServerWebExchange exchange, final GatewayFilterChain chain) {
        // 1. 获取路径
        ServerHttpRequest request = exchange.getRequest();
        ServerHttpResponse response = exchange.getResponse();
        String path = request.getURI().getPath();
        // 判断是否为内部api，如果是直接拦截
        Boolean isInnerApi = isInnerApi(path);
        if (isInnerApi) {
            return out(response, ResultCodeEnum.FAIL);
        }
        // 2. 判断是否需要登录，获取可以使用GatewayFilter，在使用配置文件
        boolean isNeedLogin = judgeLogin(path);
        // 不需要登录，直接放行
        // 从请求头中获取这两个值
        String userTempId = getValueFromCookieOrHeader(request, UserConstants.USER_TEMP_ID);

        // 3. 判断当前用户是否登入
       String userId = hasLogin(request);
        // 被盗用
        if ("-1".equals(userId)) {
            return out(response, ResultCodeEnum.ILLEGAL_REQUEST);
        }
        // 4. 没登入
        if (isNeedLogin && StrUtil.isEmpty(userId)) {
            return out(response, ResultCodeEnum.LOGIN_AUTH);
        }

        // 设置新请求头
        ServerHttpRequest newRequest = request.mutate()
                                              .header(UserConstants.USER_TEMP_ID, userTempId)
                                              .header(UserConstants.USER_ID, userId).build();
        ServerWebExchange newExChange = exchange.mutate().request(newRequest).build();
        return chain.filter(newExChange);
    }

    private Boolean isInnerApi(final String path) {
        return antPathMatcher.match(innerApiUrl, path);
    }

    /**
     * 获取用户信息
     * null : 说明用户未登录
     * -1 被盗用了
     * 用户nickname
     *
     * @param request
     * @return
     */
    private String hasLogin(final ServerHttpRequest request) {
        // 获取令牌
        // 从请求head token中获取token
        String token = request.getHeaders().getFirst(UserConstants.USER_LOGIN_TOKEN_HEADER);
        if (StrUtil.isBlank(token)) {
            // 从cookie中获取token
            HttpCookie cookie = request.getCookies().getFirst(UserConstants.USER_LOGIN_TOKEN_HEADER);
            if (cookie == null || StrUtil.isBlank(cookie.getValue())) {
                return null;
            }
            token = cookie.getValue();
        }

        // 从redis查询，获取用户信息
        String key = UserConstants.USER_LOGIN_KEY_PREFIX + token;
        RBucket<UserLoginInfoDTO> bucket = redissonClient.getBucket(key);
        UserLoginInfoDTO userLoginInfoDTO = bucket.get();
        if (userLoginInfoDTO == null) {
            return null;
        }
        // 判断令牌是否有效
        // 5. 验证用户ip，判断是否为同一个设备
        String ip = userLoginInfoDTO.getIp();// 登入ip
        String currentIp = IpUtil.getGatewayIpAddress(request);
        if (!ip.equals(currentIp)) {
            return "-1";
        }
        return userLoginInfoDTO.getUserId();
    }

    /**
     * 判断当前路径的请求是否需要登录
     *
     * @param path
     * @return
     */
    private boolean judgeLogin(final String path) {
        return antPathMatcher.match(authUrl, path);
    }

    // 接口鉴权失败返回数据
    private Mono<Void> out(ServerHttpResponse response, ResultCodeEnum resultCodeEnum) {
        // 返回用户没有权限登录
        Result<Object> result = Result.build(null, resultCodeEnum);
        // 将result对象转化为json字符串，并将字符串转化为字节数据
        byte[] bits = JSONObject.toJSONString(result).getBytes(StandardCharsets.UTF_8);
        // 封装一个字节数据为一个DataBuffer，消息体数据
        DataBuffer wrap = response.bufferFactory().wrap(bits);
        response.getHeaders().add("Content-Type", "application/json;charset=UTF-8");
        // 输出到页面
        return response.writeWith(Mono.just(wrap));
    }
}
