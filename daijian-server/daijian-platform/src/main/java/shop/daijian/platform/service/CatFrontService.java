package shop.daijian.platform.service;

import com.baomidou.mybatisplus.extension.service.IService;
import shop.daijian.platform.entity.CatFront;

import java.util.List;

/**
 * <p>
 * 前端分类 服务类
 * </p>
 *
 * @author liuxin
 * @since 2019-08-07
 */
public interface CatFrontService extends IService<CatFront> {
    /**
     * 获取前端分类子列表
     *
     * @param pId 父分类id
     * @return 子分类列表
     */
    List<CatFront> listSon(String pId);
}
