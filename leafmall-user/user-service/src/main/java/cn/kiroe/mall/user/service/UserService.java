package cn.kiroe.mall.user.service;


import cn.kiroe.mall.user.dto.UserLoginDTO;
import cn.kiroe.mall.user.query.UserInfoParam;

public interface UserService {

    /**
     * 登录方法
     */
    UserLoginDTO login(UserInfoParam userInfo, String ip, String token);

}
