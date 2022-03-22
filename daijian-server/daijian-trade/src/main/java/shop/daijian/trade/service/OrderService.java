package shop.daijian.trade.service;

import shop.daijian.trade.entity.Order;
import shop.daijian.trade.form.GoodsPackForm;
import shop.daijian.trade.form.OrderForm;

import java.util.List;

/**
 * <p>
 * 订单信息 服务类
 * </p>
 *
 * @author 关栋伟
 * @since 2019-08-03
 */
public interface OrderService{


    /**
     *生成订单缓存
     *
     * @param goodsPackFormList  商品包裹列表
     * @param userId          用户ID
     * @return                订单列表
     */
    List<Order>  previewOrderList(List<GoodsPackForm> goodsPackFormList, String userId);

    /**
     * 创建订单
     *
     * @param orderForm     订单包裹列表
     * @param userId        用户ID
     */
    void creatOrder(OrderForm orderForm, String userId);

    /**
     * 取消订单
     * @param orderId
     * @param userId
     */
    void cancel(String orderId, String userId);

    /**
     * 根据orderId回滚库存
     * @param orderId
     */
    void rollBackStock(String orderId);
}
