package cn.kiroe.mall.cart.service.impl;

import cn.hutool.core.util.StrUtil;
import cn.hutool.log.Log;
import cn.kiroe.mall.cart.api.dto.CartInfoDTO;
import cn.kiroe.mall.cart.client.ProductApiClient;
import cn.kiroe.mall.cart.converter.SkuInfoConverter;
import cn.kiroe.mall.cart.service.CartService;
import cn.kiroe.mall.common.constant.RedisConst;
import cn.kiroe.mall.product.dto.SkuInfoDTO;
import lombok.RequiredArgsConstructor;
import org.redisson.api.RBucket;
import org.redisson.api.RMap;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @Author Kiro
 * @Date 2024/01/30 10:26
 **/
@Service
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {
    private final RedissonClient redissonClient;
    private final ProductApiClient productApiClient;
    private final SkuInfoConverter skuInfoConverter;

    @Override
    public void addToCart(final Long skuId, final String userId, final Integer skuNum) {
        // 1. 获取用户购物车
        // key: userId fiedl:skuId value: 购物车对象
        String key = RedisConst.USER_CART_KEY_PREFIX + userId;
        RMap<Long, CartInfoDTO> userCartMap = redissonClient.getMap(key);
        // 2. 判断是否存在这个商品
        boolean isContainsKey = userCartMap.containsKey(skuId);
        // 3. 如果存在 更新数量
        if (isContainsKey) {
            CartInfoDTO cartInfoDTO = userCartMap.get(skuId);
            cartInfoDTO.setSkuNum(cartInfoDTO.getSkuNum() + skuNum);
            // 存入
            userCartMap.put(skuId, cartInfoDTO);
        } else {// 4. 如果不存在直接添加
            // 获取商品信息
            SkuInfoDTO skuInfo = productApiClient.getSkuInfo(skuId);
            CartInfoDTO cartInfoDTO = skuInfoConverter.skuInfoToCartInfo(skuInfo, skuNum, skuId, userId);
            // 存入
            userCartMap.put(skuId, cartInfoDTO);
        }
    }

    /**
     * 获取cartList列表，如果两个都有则合并两个 购物车
     * 1.userId userTempId  空， 有
     * 2. 有，有
     * , 有，空
     *
     * @param userId
     * @param userTempId
     * @return
     */
    @Override
    public List<CartInfoDTO> getCartList(final String userId, final String userTempId) {
        // 1.获取用户购物车
        RMap<Long, CartInfoDTO> userIdMap = redissonClient.getMap(getUserCartKey(userId));
        RMap<Long, CartInfoDTO> userTempIdMap = redissonClient.getMap(getUserCartKey(userTempId));
        // 2.获取
        // 3. 当都有时需要合并,
        if (StrUtil.isNotBlank(userId) && StrUtil.isNotBlank(userTempId)) {
            // 判断userTempIdMap是否为空
            if (userTempIdMap.isEmpty()) {
                return userIdMap.values().stream().toList();
            }
            // 合并两个，遇到相同的则相加
            for (final Map.Entry<Long, CartInfoDTO> entry : userTempIdMap.entrySet()) {
                Long skuId = entry.getKey();
                CartInfoDTO cartInfoDTO = entry.getValue();
                CartInfoDTO tempCard = userIdMap.get(skuId);
                if (tempCard != null) { // 合并
                    cartInfoDTO.setSkuNum(cartInfoDTO.getSkuNum() + tempCard.getSkuNum());
                    cartInfoDTO.setUpdateTime(new Date());
                }
                userIdMap.put(skuId, cartInfoDTO);
            }
            // 删除temp
            userTempIdMap.delete();
            return userIdMap.values().stream().toList();
        } else if (StrUtil.isNotBlank(userTempId)) {// 说明只有userTempId
            return userTempIdMap.values().stream().toList();
        } else if (StrUtil.isNotBlank(userId)) {// 说明只有userid
            return userIdMap.values().stream().toList();
        }
        // 都为空，返回null
        return null;
    }

    /**
     * 选中状态的更新
     * @param userId
     * @param isChecked
     * @param skuId
     */
    @Override
    public void checkCart(final String userId, final Integer isChecked, final Long skuId) {
        RMap<Long, CartInfoDTO> cartMap = redissonClient.getMap(getUserCartKey(userId));
        CartInfoDTO cartInfoDTO = cartMap.get(skuId);
        cartInfoDTO.setIsChecked(isChecked);
        cartMap.put(skuId,cartInfoDTO);
    }

    /**
     * 从购物车中删除商品
     * @param skuId         商品id
     * @param userId        用户id
     */
    @Override
    public void deleteCart(final Long skuId, final String userId) {
        RMap<Long, CartInfoDTO> cartMap = redissonClient.getMap(getUserCartKey(userId));
        CartInfoDTO cartInfoDTO = cartMap.remove(skuId);
        if(cartInfoDTO == null){
            throw new RuntimeException("删除失败");
        }
    }

    @Override
    public void deleteChecked(final String userId) {
        RMap<Long, CartInfoDTO> cartMap = redissonClient.getMap(getUserCartKey(userId));
        for (final Map.Entry<Long, CartInfoDTO> entry : cartMap.entrySet()) {
            Long skuId = entry.getKey();
            CartInfoDTO cartInfoDTO = entry.getValue();
            if (cartInfoDTO.getIsChecked()==1){ //移除
                cartMap.remove(skuId);
            }
        }
    }
    private String getUserCartKey(String userId){
        return RedisConst.USER_CART_KEY_PREFIX + userId;
    }
    @Override
    public List<CartInfoDTO> getCartCheckedList(final String userId) {
        RMap<Long, CartInfoDTO> cartMap = redissonClient.getMap(getUserCartKey(userId));
        // 筛选出已经选中的
        return cartMap.values().stream().filter(c -> c.getIsChecked() == 1).toList();
    }

    @Override
    public void delete(final String userId, final List<Long> skuIds) {

    }

    @Override
    public void refreshCartPrice(final String userId, final Long skuId) {

    }
}
