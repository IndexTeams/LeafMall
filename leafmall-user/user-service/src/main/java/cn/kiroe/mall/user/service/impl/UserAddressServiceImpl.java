package cn.kiroe.mall.user.service.impl;

import cn.kiroe.mall.user.converter.UserAddressConverter;
import cn.kiroe.mall.user.dto.UserAddressDTO;
import cn.kiroe.mall.user.mapper.UserAddressMapper;
import cn.kiroe.mall.user.model.UserAddress;
import cn.kiroe.mall.user.service.UserAddressService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author Kiro
 * @Date 2024/02/01 10:31
 **/
@Service
@RequiredArgsConstructor
public class UserAddressServiceImpl implements UserAddressService {
    private final UserAddressMapper userAddressMapper;
    private final UserAddressConverter userAddressConverter;

    @Override
    public List<UserAddressDTO> findUserAddressListByUserId(final String userId) {
        List<UserAddress> userAddresses = userAddressMapper.selectList(new LambdaQueryWrapper<UserAddress>().eq(UserAddress::getUserId, Long.parseLong(userId)));
        return userAddressConverter.userAddressPOs2DTOs(userAddresses);
    }
}
