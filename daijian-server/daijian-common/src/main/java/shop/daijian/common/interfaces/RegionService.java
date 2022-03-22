package shop.daijian.common.interfaces;

/**
 * <p>
 * 地区码表 服务类
 * </p>
 *
 * @author liuxin
 * @since 2019-08-12
 */
public interface RegionService {

    /**
     * 查询一个地区及其父地区
     *
     * @param regionId
     * @return
     */
    String getRegion(String regionId);

}
