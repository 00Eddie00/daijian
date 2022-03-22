package shop.daijian.seller.repository.mybatis;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import shop.daijian.seller.entity.ShopInfo;

import java.util.List;


/**
 * <p>
 * 店铺信息 Mapper 接口
 * </p>
 *
 * @author hanshizhou
 * @since 2019-08-04
 */

public interface ShopInfoMapper extends BaseMapper<ShopInfo> {

    /**
     * 通过店铺ID列表查询店铺信息列表（保证顺序，但不保证缺失）
     */
    List<ShopInfo> selectByIdList(@Param("shopIdList") List<String> shopIdList);

}
