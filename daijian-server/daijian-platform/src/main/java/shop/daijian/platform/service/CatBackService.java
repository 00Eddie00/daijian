package shop.daijian.platform.service;

import com.baomidou.mybatisplus.extension.service.IService;
import shop.daijian.platform.entity.CatBack;

import java.util.List;

/**
 * <p>
 * 商品后台分类 服务类
 * </p>
 *
 * @author liuxin
 * @since 2019-08-07
 */
public interface CatBackService extends IService<CatBack> {

    /**
     * 通过后台分类id递归获取其及其所有父分类列表
     *
     * @param catBackId   后台分类id
     * @param catBackList 当前分类列表，开始为空
     * @return 父分类列表
     */
    List<CatBack> searchTree(String catBackId, List<CatBack> catBackList);

}
