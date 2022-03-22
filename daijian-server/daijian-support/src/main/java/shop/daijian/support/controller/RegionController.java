package shop.daijian.support.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import shop.daijian.common.exception.BizException;
import shop.daijian.common.support.BaseController;
import shop.daijian.common.wrapper.ResultWrapper;
import shop.daijian.support.entity.RegionInfo;
import shop.daijian.support.enums.RegionStatusEnum;
import shop.daijian.support.service.RegionInfoService;
import shop.daijian.support.vo.RegionInfoVO;

import java.util.List;

import static shop.daijian.common.util.BeanUtil.transList;

/**
 * <p>
 * 地区码表 前端控制器
 * </p>
 *
 * @author liuxin
 * @since 2019-08-12
 */
@Api(tags = "地区接口")
@RestController
@RequestMapping("/region")
public class RegionController extends BaseController {

    @Autowired
    private RegionInfoService regionService;


    @ApiOperation("获取一个地区的所有子地区的列表")
    @GetMapping("/sonList/{regionId}")
    public ResultWrapper<List<RegionInfoVO>> listAllSon(@ApiParam("父区域id") @PathVariable String regionId) {
        QueryWrapper<RegionInfo> wrapper = new QueryWrapper<>();
        wrapper.eq("pid", regionId);
        List<RegionInfo> regionInfoList = regionService.list(wrapper);
        if (regionInfoList.size() == 0) {
            throw new BizException(RegionStatusEnum.REGION_NOT_EXIST);
        }
        return ResultWrapper.success(transList(regionInfoList, RegionInfoVO.class));
    }


}
