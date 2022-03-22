package shop.daijian.trade.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import shop.daijian.trade.entity.OrderInfo;
import shop.daijian.trade.enums.OrderStateEnum;
import shop.daijian.trade.repository.mybatis.OrderInfoMapper;
import shop.daijian.trade.service.OrderInfoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;

import static java.util.Arrays.asList;

/**
 * <p>
 * 订单信息 服务实现类
 * </p>
 *
 * @author 关栋伟
 * @author hanshizhou
 * @since 2019-08-03
 */
@Service
@AllArgsConstructor
public class OrderInfoServiceImpl extends ServiceImpl<OrderInfoMapper, OrderInfo> implements OrderInfoService {

    @Override
    public IPage<OrderInfo> pageByUserId(IPage<OrderInfo> orderInfoPage, String userId) {
        //把orderInfo分页
        QueryWrapper<OrderInfo> wrapper = new QueryWrapper<>();
        wrapper.eq("user_id", userId);
        IPage<OrderInfo> pageResult = page(orderInfoPage, wrapper);
        return pageResult;
    }

    @Override
    public IPage<OrderInfo> pageByUserIdAndState(IPage<OrderInfo> orderInfoPage, OrderStateEnum orderState, String userId) {
        //把orderInfo分页
        QueryWrapper<OrderInfo> wrapper = new QueryWrapper<>();
        wrapper.eq("user_id", userId).eq("state", orderState);
        IPage<OrderInfo> pageResult = page(orderInfoPage, wrapper);
        return pageResult;
    }

    @Override
    public String getShopIdByOrderId(String orderId) {
        OrderInfo orderInfo = getById(orderId);
        return orderInfo.getShopId();
    }

    @Override
    public List<OrderInfo> getOrderInfoListByOrderIdList(List<String> orderIdList) {
        return new ArrayList<>(listByIds(orderIdList));
    }

    @Override
    public Map<OrderStateEnum, Integer> getOrderStateNumsMap(String userId) {

        // 创建mapOrderStateNums方法中用于存储<状态类型, 对应数量> 的键值对
        Map<OrderStateEnum, Integer> orderStateNumsMap = new ConcurrentHashMap<OrderStateEnum, Integer>();

        // 运用多个线程对Map进行填充
        List<OrderStateEnum> orderStateEnumList = asList(
                OrderStateEnum.WAIT_PAY,
                OrderStateEnum.WAIT_SEND,
                OrderStateEnum.WAIT_CONFIRM
        );

        // 创建线程池
        ExecutorService pool = Executors.newFixedThreadPool(orderStateEnumList.size());

        //启动线程
        orderStateEnumList.stream().forEach(orderStateEnum -> {
                    Runnable task = () ->
                            orderStateNumsMap.put(orderStateEnum,
                                    count(new QueryWrapper<OrderInfo>()
                                            .eq("state", orderStateEnum)
                                            .eq("user_id", userId)));
                    pool.execute(task);
                }
        );

        // 发送线程池终止命令
        pool.shutdown();

        // 等待所有线程结束
        while (!pool.isTerminated()) {
        }
        ;

        return orderStateNumsMap;
    }

}
