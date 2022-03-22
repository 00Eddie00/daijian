package shop.daijian.trade.repository.redis;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import shop.daijian.common.enums.RedisKeyEnum;
import shop.daijian.common.support.BaseRedisTemplate;
import shop.daijian.trade.entity.Order;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * 订单redis数据访问层
 *
 * @author guandongwei
 * @since 2019/8/4
 */
@Repository
@AllArgsConstructor
public class OrderRedisTemplate {

    private BaseRedisTemplate<Order> template;

    /**
     * 有效时间
     */
    private static final Long EXPIRE = 1L;

    /**
     * 时间单位
     */
    private static final TimeUnit TIME_UNIT = TimeUnit.HOURS;

    public void set(List<Order> orderList, String userId) {
        for (Order order : orderList) {
            template.set(RedisKeyEnum.ORDER.buildKey(order.getOrderInfo().getOrderId(),userId),
                    order, EXPIRE, TIME_UNIT);
        }
    }

    public List<Order> get(List<String> orderIdList, String userId) {

        List<String> orderKeyList = new ArrayList<>();
        for (String orderId : orderIdList) {
            orderKeyList.add(RedisKeyEnum.ORDER.buildKey(orderId,userId,Order.class));
        }
        return template.multiGet(orderKeyList,Order.class);
    }

    public void delete(String orderId, String userId){
        template.delete(RedisKeyEnum.ORDER.buildKey(orderId,userId));
    }

    public void deleteList(List<String> orderIdList, String userId){

        for (String orderId : orderIdList) {
            template.delete(RedisKeyEnum.ORDER.buildKey(orderId,userId));
        }
    }

}
