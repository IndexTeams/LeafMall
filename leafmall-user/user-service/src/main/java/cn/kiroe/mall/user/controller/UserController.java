package cn.kiroe.mall.user.controller;

import cn.kiroe.mall.common.result.Result;
import cn.kiroe.mall.common.util.IpUtil;
import cn.kiroe.mall.user.consts.UserCodeEnum;
import cn.kiroe.mall.user.consts.UserConstants;
import cn.kiroe.mall.user.dto.UserLoginDTO;
import cn.kiroe.mall.user.query.UserInfoParam;
import cn.kiroe.mall.user.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.redisson.api.RBucket;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

/**
 * @Author Kiro
 * @Date 2024/01/29 11:17
 **/
@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private RedissonClient redissonClient;

    /**
     * 登入
     */
    @PostMapping("login")
    public Result login(@RequestBody UserInfoParam userInfo, HttpServletRequest request) {

        // 从当前请求中，获取请求发起的IP地址
        String ipAddress = IpUtil.getIpAddress(request);
        // 生成uuid字符串，作为Redis存储登录会话的key
        String token = UUID.randomUUID().toString();
        UserLoginDTO loginInfo = userService.login(userInfo, ipAddress, token);
        if (loginInfo == null) {
            // 登录失败
            return Result.build("登入失败", UserCodeEnum.USER_LOGIN_CHECK_FAIL);
        }
        // 登录匹配成功
        return Result.ok(loginInfo);
    }
    /**
     * 退出登录
     * @param request
     * @return
     */
    @GetMapping("logout")
    public Result logout(HttpServletRequest request){
        RBucket<String> token = redissonClient.getBucket(UserConstants.USER_LOGIN_KEY_PREFIX + request.getHeader(UserConstants.USER_LOGIN_TOKEN_HEADER));
        if(token==null || !token.delete()){
            Result.fail("退出登录失败");
        }
        return Result.ok();
    }



}
