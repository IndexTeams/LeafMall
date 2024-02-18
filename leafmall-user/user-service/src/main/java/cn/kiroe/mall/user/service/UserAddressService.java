package cn.kiroe.mall.user.service;


import cn.kiroe.mall.user.dto.UserAddressDTO;
import cn.kiroe.mall.user.model.UserAddress;

import java.util.List;

public interface UserAddressService {

    /**
     * 根据用户Id 查询用户的收货地址列表！
     * @param userId
     * @return
     */
    List<UserAddressDTO> findUserAddressListByUserId(String userId);
}
