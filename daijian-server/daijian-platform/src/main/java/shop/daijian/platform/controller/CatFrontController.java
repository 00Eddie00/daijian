package shop.daijian.platform.controller;


import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import shop.daijian.common.wrapper.ResultWrapper;
import shop.daijian.platform.entity.CatFront;
import shop.daijian.platform.service.CatFrontService;
import shop.daijian.platform.vo.CatFrontVO;

import java.util.List;

import static shop.daijian.common.util.BeanUtil.transList;

/**
 * <p>
 * 前端分类 前端控制器
 * </p>
 *
 * @author liuxin
 * @since 2019-08-07
 */
@Api(tags = {"前端分类接口"})
@RestController
@RequestMapping("/front")
public class CatFrontController {

    @Autowired
    CatFrontService catFrontService;

    @ApiOperation("获取分类列表")
    @GetMapping("/sonList/{pId}")
    public ResultWrapper<List<CatFrontVO>> listCatBackSon(@ApiParam("父分类id") @PathVariable String pId) {
        List<CatFront> catFrontList = catFrontService.listSon(pId);
        List<CatFrontVO> catFrontVOList = transList(catFrontList, CatFrontVO.class);
        return ResultWrapper.success(catFrontVOList);
    }

}
