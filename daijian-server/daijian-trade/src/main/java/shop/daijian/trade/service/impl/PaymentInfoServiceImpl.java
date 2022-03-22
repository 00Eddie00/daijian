package shop.daijian.trade.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import shop.daijian.trade.entity.PaymentInfo;
import shop.daijian.trade.repository.mybatis.AlipayInfoMapper;
import shop.daijian.trade.service.PaymentInfoService;
/**
 * <p>
 * 支付信息表 服务实现类
 * </p>
 *
 * @author 关栋伟
 * @since 2019-08-16
 */
@Service
public class PaymentInfoServiceImpl extends ServiceImpl<AlipayInfoMapper, PaymentInfo> implements PaymentInfoService {

}
