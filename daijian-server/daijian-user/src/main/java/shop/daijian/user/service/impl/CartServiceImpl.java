package shop.daijian.user.service.impl;

import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;
import shop.daijian.user.dto.CartDTO;
import shop.daijian.user.repository.redis.CartTemplate;
import shop.daijian.user.service.CartService;

import java.util.List;

/**
 * @author hanshizhou
 * @description: CartService的实现类
 * @since 2019/8/12 17:28
 **/
@Service
public class CartServiceImpl implements CartService {

    @Autowired
    private CartTemplate cartTemplate;

    @Override
    public void goodsPack(String userId, String goodsId, Integer goodsNum) {
        CartDTO cartDTO = new CartDTO();
        //判断购物车是否存在,没有就建一个
        if(cartTemplate.exist(userId)){
            //不可能存在CART_NOT_EXIST
            cartDTO = cartTemplate.get(userId);
            Integer usedNum = cartDTO.get(goodsId);
            // 分为购物车有无此商品两种情况
            cartDTO.put(goodsId, usedNum == null ? goodsNum : usedNum + goodsNum);
        }else {
            cartDTO = new CartDTO();
            cartDTO.put(goodsId, goodsNum);
        }
        //做好准备工作后更新缓存数据
        cartTemplate.set(userId, cartDTO);
    }

    @Override
    public void reduceGoodsNum(String userId, String goodsId, Integer goodsNum) {
        //不可能存在CART_NOT_EXIST
        CartDTO cartDTO = cartTemplate.get(userId);
        Integer usedNum = cartDTO.get(goodsId);
        // 分为购物车有无此商品两种情况
        if(usedNum - goodsNum <= 0){
            cartDTO.remove(goodsId);
        }else {
            cartDTO.put(goodsId, usedNum - goodsNum);
        }
        cartTemplate.set(userId, cartDTO);
    }

    @Override
    public void deleteGoods(String userId, List<String> goodsIdList) {
        if (cartTemplate.exist(userId)) {
            CartDTO cartDTO = cartTemplate.get(userId);
            for (String goodsId : goodsIdList) {
                cartDTO.remove(goodsId);
            }
            cartTemplate.set(userId, cartDTO);
        }
    }

    @Override
    public CartDTO getCart(String userId){
        return cartTemplate.get(userId);
    }

}
