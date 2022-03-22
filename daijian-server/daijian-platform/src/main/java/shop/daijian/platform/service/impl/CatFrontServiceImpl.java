package shop.daijian.platform.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import shop.daijian.platform.entity.CatFront;
import shop.daijian.platform.repository.mybatis.CatFrontMapper;
import shop.daijian.platform.service.CatFrontService;

import java.util.List;

/**
 * <p>
 * 前端分类 服务实现类
 * </p>
 *
 * @author liuxin
 * @since 2019-08-07
 */
@Service
public class CatFrontServiceImpl extends ServiceImpl<CatFrontMapper, CatFront> implements CatFrontService {

    @Autowired(required = false)
    private CatFrontMapper catFrontMapper;

    @Override
    public List<CatFront> listSon(String pId) {
        return catFrontMapper.selectListBypIdGetFront(pId);
    }

}
