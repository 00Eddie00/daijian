package shop.daijian.support.service.impl;/**
 * @author suyutong
 * @since 2019/8/15
 */

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import shop.daijian.common.exception.BizException;
import shop.daijian.support.entity.RegionInfo;
import shop.daijian.support.enums.RegionStatusEnum;
import shop.daijian.support.mybatis.RegionInfoMapper;
import shop.daijian.support.service.RegionInfoService;

import java.util.Stack;

/**
 * @author suyutong
 * @since 2019/8/15 11:42
 */
@Service
public class RegionInfoServiceImpl extends ServiceImpl<RegionInfoMapper, RegionInfo> implements RegionInfoService {

    @Autowired
    RegionInfoService regionInfoService;

    @Override
    public String getRegionFullText(String regionId) {
        Stack<String> stack = new Stack<>();
        StringBuffer region = new StringBuffer();
        //查询区级区域
        RegionInfo subRegion = regionInfoService.getById(regionId);
        //判断该地区是否存在
        if (subRegion == null) {
            throw new BizException(RegionStatusEnum.REGION_NOT_EXIST);
        }
        String district = subRegion.getName();
        //判断该地区是否有父地区
        if (subRegion.getPid() == -1) {
            return subRegion.getName();
        }
        //存入栈中
        stack.push(district);
        //查询上级地区
        QueryWrapper<RegionInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("region_id", subRegion.getPid());
        RegionInfo parentRegion = regionInfoService.getOne(queryWrapper);
        //判断该地区是否有父地区
        if (parentRegion.getPid() == -1) {
            return parentRegion.getName() + " " + stack.peek();
        }
        String city = parentRegion.getName();
        //存入栈中
        stack.push(city);
        //查询省级地区
        QueryWrapper<RegionInfo> wrapper = new QueryWrapper<>();
        wrapper.eq("region_id", parentRegion.getPid());
        RegionInfo provinceInfo = regionInfoService.getOne(wrapper);
        //若其父级地区为-1，则返回详细地址
        if (provinceInfo.getPid() == -1) {
            String province = provinceInfo.getName();
            //存入栈中
            stack.push(province);
            for (int i = 0; i < 3; i++) {
                region.append(stack.pop()).append(" ");
            }
            return region.toString();
        }
        return null;
    }
}
