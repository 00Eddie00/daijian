package shop.daijian.platform.repository.mybatis;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import shop.daijian.platform.entity.CatFront;

import java.util.List;

/**
 * <p>
 * 前端分类 Mapper 接口
 * </p>
 *
 * @author liuxin
 * @since 2019-08-07
 */
public interface CatFrontMapper extends BaseMapper<CatFront> {

    /**
     * 通过父分类获取前端分类列表
     *
     * @param pId 父分类id
     * @return 前端分类列表
     */
    default List<CatFront> selectListBypIdGetFront(String pId) {
        QueryWrapper<CatFront> wrapper = new QueryWrapper<>();
        wrapper.eq("pid", pId);
        return this.selectList(wrapper);
    }

}
