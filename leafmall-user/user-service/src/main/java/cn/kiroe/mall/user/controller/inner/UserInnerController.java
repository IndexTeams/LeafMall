package cn.kiroe.mall.user.controller.inner;

import cn.kiroe.mall.user.converter.UserAddressConverter;
import cn.kiroe.mall.user.dto.UserAddressDTO;
import cn.kiroe.mall.user.model.UserAddress;
import cn.kiroe.mall.user.service.UserAddressService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @Author Kiro
 * @Date 2024/02/01 10:33
 **/
@RestController
@RequiredArgsConstructor
public class UserInnerController {
    private final UserAddressService userAddressService;
    @GetMapping("/api/user/inner/findUserAddressListByUserId/{userId}")
    public List<UserAddressDTO> findUserAddressListByUserId(@PathVariable("userId") String userId){
        return userAddressService.findUserAddressListByUserId(userId);
    }
}
