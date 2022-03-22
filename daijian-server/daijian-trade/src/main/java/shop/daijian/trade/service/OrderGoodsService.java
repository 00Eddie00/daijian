package shop.daijian.trade.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import shop.daijian.common.vo.PageVO;
import shop.daijian.trade.entity.OrderGoods;
import com.baomidou.mybatisplus.extension.service.IService;
import shop.daijian.trade.enums.OrderStateEnum;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 订单商品 服务类
 * </p>
 *
 * @author 关栋伟
 * @author hanshizhou
 * @since 2019-08-04
 */
public interface OrderGoodsService extends IService<OrderGoods> {

    /**
     * 通过订单ID查询订单商品列表
     *
     * @param orderId
     * @return
     */
    List<OrderGoods> getGoodsListByOrderId(String orderId);

    /**
     * 根据order_goods_id得到goods_id
     */
    String getGoodsIdByOrderGoodsId(String OrderGoodsId);

    /**
     * 改变订单中商品的状态为待评价
     *
     * @param orderId
     */
    void setWaitComment(String orderId);

    /**
     * 通过orderId获得订单中订单商品的goodsIdList
     */
    List<String> getGoodsIdListByOrderId(String orderId, String userId);

    /**
     * 通过orderId和GoodsId获得orderGoods
     *
     * @param orderId
     * @param goodsId
     * @return
     */
    OrderGoods getByOrderIdAndGoodsId(String orderId, String goodsId);

    /**
     * 分页获取一个月内用户的购买商品ID列表
     */
    PageVO<String> pageRecentGoodsId(IPage<OrderGoods> page, String userId);

    /**
     *  通过用户ID获取WAIT_COMMENT状态订单的数量
     *
     * @param userId
     * @return
     */
    Map<OrderStateEnum, Integer> getOrderStateNumMap(String userId);
}
