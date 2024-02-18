package cn.kiroe.mall.user.converter;


import cn.kiroe.mall.user.dto.UserAddressDTO;
import cn.kiroe.mall.user.model.UserAddress;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserAddressConverter {

    UserAddressDTO userAddressPO2DTO(UserAddress userAddress);
    List<UserAddressDTO> userAddressPOs2DTOs(List<UserAddress> userAddresses);
}
