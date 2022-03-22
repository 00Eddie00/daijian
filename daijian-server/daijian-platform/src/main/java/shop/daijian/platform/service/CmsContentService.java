package shop.daijian.platform.service;

import com.baomidou.mybatisplus.extension.service.IService;
import shop.daijian.platform.entity.CmsContent;

import java.util.List;

/**
 * <p>
 * cms内容 服务类
 * </p>
 *
 * @author hsz
 * @since 2019-08-26
 */
public interface CmsContentService extends IService<CmsContent> {

    /**
     * 根据位置获取对应内容
     *
     * @param cmsSiteId 页面位置
     * @param type 具体类型
     * @return 内容列表
     */
    List<CmsContent> listCms(String cmsSiteId, String type);
}
