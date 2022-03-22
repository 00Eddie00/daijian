package shop.daijian.seller.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import shop.daijian.seller.entity.ShopInfo;
import shop.daijian.seller.repository.mybatis.ShopInfoMapper;
import shop.daijian.seller.service.ShopInfoService;


/**
 * <p>
 * 店铺信息 服务实现类
 * </p>
 *
 * @author hanshizhou
 * @since 2019-08-04
 */
@Service
public class ShopInfoServiceImpl extends ServiceImpl<ShopInfoMapper, ShopInfo> implements ShopInfoService {

}
