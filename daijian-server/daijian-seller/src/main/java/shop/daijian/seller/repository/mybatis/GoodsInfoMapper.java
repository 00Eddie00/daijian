package shop.daijian.seller.repository.mybatis;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import shop.daijian.seller.entity.GoodsInfo;

import java.util.List;

/**
 * <p>
 * 商品信息 Mapper 接口
 * </p>
 *
 * @author liuxin
 * @since 2019-08-04
 */
public interface GoodsInfoMapper extends BaseMapper<GoodsInfo> {

    /**
     * 通过商品ID列表查询商品信息列表（保证顺序，但不保证缺失）
     */
    List<GoodsInfo> selectByIdList(@Param("goodsIdList") List<String> goodsIdList);
}
