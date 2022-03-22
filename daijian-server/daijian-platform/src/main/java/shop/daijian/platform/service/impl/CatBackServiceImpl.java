package shop.daijian.platform.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import shop.daijian.platform.entity.CatBack;
import shop.daijian.platform.repository.mybatis.CatBackMapper;
import shop.daijian.platform.service.CatBackService;

import java.util.List;

/**
 * <p>
 * 商品后台分类 服务实现类
 * </p>
 *
 * @author liuxin
 * @since 2019-08-07
 */
@Service
public class CatBackServiceImpl extends ServiceImpl<CatBackMapper, CatBack> implements CatBackService {

    @Autowired(required = false)
    private CatBackMapper catBackMapper;

    @Override
    public List<CatBack> searchTree(String catBackId, List<CatBack> catBackList) {
        CatBack catBack = catBackMapper.selectById(catBackId);
        //判断catBack是否为空
        if (catBack == null) {
            return catBackList;
        }
        catBackList.add(catBack);
        return searchTree(catBack.getPid(), catBackList);
    }

}
