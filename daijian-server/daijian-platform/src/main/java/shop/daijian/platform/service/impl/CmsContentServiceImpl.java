package shop.daijian.platform.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import shop.daijian.platform.entity.CmsContent;
import shop.daijian.platform.repository.mybatis.CmsContentMapper;
import shop.daijian.platform.service.CmsContentService;

import java.util.List;

/**
 * <p>
 * cms内容 服务实现类
 * </p>
 *
 * @author hsz
 * @since 2019-08-26
 */
@Service
public class CmsContentServiceImpl extends ServiceImpl<CmsContentMapper, CmsContent> implements CmsContentService {

    @Override
    public List<CmsContent> listCms(String cmsSiteId, String type) {
        QueryWrapper<CmsContent> wrapper = new QueryWrapper<>();
        wrapper.eq("cms_site_id", cmsSiteId).eq("type", type);
        return list(wrapper);
    }
}
