package shop.daijian.trade.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import shop.daijian.trade.entity.OrderInfo;
import com.baomidou.mybatisplus.extension.service.IService;
import shop.daijian.trade.enums.OrderStateEnum;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 订单信息 服务类
 * </p>
 *
 * @author 关栋伟
 * @author hanshizhou
 * @since 2019-08-03
 */
public interface OrderInfoService extends IService<OrderInfo> {

    /**
     *通过userId对orderInfo分页
     *
     * @param orderInfoPage
     * @param userId
     * @return
     */
    IPage<OrderInfo> pageByUserId(IPage<OrderInfo> orderInfoPage, String userId);

    /**
     * 通过userId和订单状态对orderInfo分页
     *
     * @param orderInfoPage
     * @param orderState
     * @param userId
     * @return
     */
    IPage<OrderInfo> pageByUserIdAndState(IPage<OrderInfo> orderInfoPage, OrderStateEnum orderState, String userId);

    /**
     * 通过orderId获取shopId
     * @param orderId
     * @return
     */
    String getShopIdByOrderId(String orderId);

    /**
     * 通过OrderIdList获取OrderInfoList
     * @param orderIdList
     * @return
     */
    List<OrderInfo> getOrderInfoListByOrderIdList(List<String> orderIdList);

    /**
     *  通过用户ID获取每个状态订单的数量
     *
     * @param userId
     * @return
     */
    Map<OrderStateEnum, Integer> getOrderStateNumsMap(String userId);

}
