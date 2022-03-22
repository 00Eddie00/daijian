package shop.daijian.seller.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import shop.daijian.seller.entity.GoodsInfo;
import shop.daijian.seller.repository.mybatis.GoodsInfoMapper;
import shop.daijian.seller.service.GoodsInfoService;

/**
 * <p>
 * 商品信息 服务实现类
 * </p>
 *
 * @author liuxin
 * @since 2019-08-04
 */
@Service
public class GoodsInfoServiceImpl extends ServiceImpl<GoodsInfoMapper, GoodsInfo> implements GoodsInfoService {

}
