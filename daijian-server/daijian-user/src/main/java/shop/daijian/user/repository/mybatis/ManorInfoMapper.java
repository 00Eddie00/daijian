package shop.daijian.user.repository.mybatis;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import shop.daijian.user.entity.ManorInfo;


import java.util.List;

/**
 * <p>
 * 庄园信息 Mapper 接口
 * </p>
 *
 * @author qiyubing
 * @since 2019-08-12
 */
public interface ManorInfoMapper extends BaseMapper<ManorInfo> {

    /**
     * 通过庄园主用户ID列表查询庄园信息列表（保证顺序，但不保证缺失）
     */
    List<ManorInfo> selectByIdList(@Param("userIdList") List<String> userIdList);
}
