package shop.daijian.user.repository.mybatis;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import shop.daijian.user.entity.NotesInfo;

import java.util.List;

/**
 * <p>
 * 手记 Mapper 接口
 * </p>
 *
 * @author qiyubing
 * @since 2019-08-12
 */
public interface NotesInfoMapper extends BaseMapper<NotesInfo> {

    /**
     * 通过手记ID列表查询手记信息列表（保证顺序，但不保证缺失）
     */
    List<NotesInfo> selectByIdList(@Param("notesIdList") List<String> notesIdList);
}
