package shop.daijian.user.service;

import shop.daijian.user.dto.CartDTO;

import java.util.List;

/**
 * 这里进行购物车业务逻辑的封装
 *
 * @author hsz
 * @since 2019/08/11
 */
public interface CartService {

    /**
     * 增加商品数量，若之前不存在此商品，店铺或购物车的话就新建一个
     * @param userId 用户ID
     * @param goodsId 商品ID
     * @param goodsNum 商品数量
     */
    void goodsPack(String userId, String goodsId, Integer goodsNum);

    /**
     * 减商品数量，减至0就将商品，店铺从购物车移除
     * @param userId 用户ID
     * @param goodsId 商品ID
     * @param goodsNum 减少的数量
     */
    void reduceGoodsNum(String userId, String goodsId, Integer goodsNum);

    /**
     * 批量移除商品
     * @param userId 用户ID
     * @param goodsIdList 要移除商品的列表
     */
    void deleteGoods(String userId, List<String> goodsIdList);

    /**
     * 获取用户购物车
     *
     */
    CartDTO getCart(String userId);

}
