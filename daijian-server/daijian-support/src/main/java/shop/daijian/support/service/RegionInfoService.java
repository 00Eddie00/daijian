package shop.daijian.support.service;

import com.baomidou.mybatisplus.extension.service.IService;
import shop.daijian.support.entity.RegionInfo;

/**
 * <p>
 * 地区码表 服务类
 * </p>
 *
 * @author liuxin
 * @since 2019-08-12
 */
public interface RegionInfoService extends IService<RegionInfo> {

    /**
     * 查询一个地区及其父地区
     *
     * @param regionId
     * @return
     */
    String getRegionFullText(String regionId);
}
