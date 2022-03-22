package shop.daijian.support.service.impl;

import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;
import shop.daijian.common.interfaces.RegionService;
import shop.daijian.support.service.RegionInfoService;

/**
 * <p>
 * 地区码表 服务实现类
 * </p>
 *
 * @author liuxin
 * @since 2019-08-12
 */
@Service
public class RegionServiceImpl implements RegionService {

    @Autowired
    RegionInfoService regionInfoService;

    @Override
    public String getRegion(String regionId) {
        return regionInfoService.getRegionFullText(regionId);
    }
}

