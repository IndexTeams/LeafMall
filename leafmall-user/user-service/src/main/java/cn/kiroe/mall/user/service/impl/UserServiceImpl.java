package cn.kiroe.mall.user.service.impl;

import cn.kiroe.mall.user.consts.UserConstants;
import cn.kiroe.mall.user.dto.UserLoginDTO;
import cn.kiroe.mall.user.dto.UserLoginInfoDTO;
import cn.kiroe.mall.user.mapper.UserInfoMapper;
import cn.kiroe.mall.user.model.UserInfo;
import cn.kiroe.mall.user.query.UserInfoParam;
import cn.kiroe.mall.user.service.UserService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import lombok.RequiredArgsConstructor;
import org.redisson.api.RBucket;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

/**
 * @Author Kiro
 * @Date 2024/01/29 11:19
 **/
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserInfoMapper userInfoMapper;
    private final RedissonClient redissonClient;

    @Override
    public UserLoginDTO login(final UserInfoParam userInfoParam, final String ip, final String token) {
        // 1. 根据用户名于密码，查询数据库
        String loginName = userInfoParam.getLoginName();
        String passwd = userInfoParam.getPasswd();
        String passwdMD5 = DigestUtils.md5DigestAsHex(passwd.getBytes());
        UserInfo userInfo = userInfoMapper.selectOne(new LambdaQueryWrapper<UserInfo>().eq(UserInfo::getLoginName, loginName).eq(UserInfo::getPasswd, passwdMD5));

        // 2. 没查到，登录失败
        if (userInfo == null) {
            return null;
        }
        // 3. 登录成功
        // 4. 将登录信息和token保存至redis
        // 设置值
        UserLoginInfoDTO userLoginInfoDTO = new UserLoginInfoDTO();
        userLoginInfoDTO.setIp(ip);
        userLoginInfoDTO.setUserId(String.valueOf(userInfo.getId()));
        // 存入redis
        RBucket<UserLoginInfoDTO> bucket = redissonClient.getBucket(UserConstants.USER_LOGIN_KEY_PREFIX + token);
        bucket.set(userLoginInfoDTO);

        // 5. 返回令牌
        return new UserLoginDTO(userInfo.getNickName(), token);
    }
}
