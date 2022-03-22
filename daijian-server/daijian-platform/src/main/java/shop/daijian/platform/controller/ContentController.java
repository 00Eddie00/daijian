package shop.daijian.platform.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import shop.daijian.common.dto.GoodsBriefDTO;
import shop.daijian.common.dto.NotesBriefDTO;
import shop.daijian.common.dto.ShopBriefDTO;
import shop.daijian.common.form.PageForm;

import shop.daijian.common.interfaces.GoodsService;
import shop.daijian.common.util.BeanUtil;

import shop.daijian.common.vo.PageVO;
import shop.daijian.common.wrapper.ResultWrapper;
import shop.daijian.platform.entity.CmsContent;
import shop.daijian.platform.service.CmsContentService;
import shop.daijian.platform.vo.CmsContentVO;

import shop.daijian.platform.service.RecommendService;


import java.util.List;

/**
 * 内容管理
 *
 * @author qiyubing
 * @author suyutong
 * @author hanshizhou
 * @since 2019-08-24
 */
@Api(tags = "内容接口")
@RestController
@RequestMapping("/content")
public class ContentController {

    @Autowired
    private RecommendService recommendService;

    @Autowired
    private CmsContentService cmsContentService;

    @Reference
    private GoodsService goodsService;

    @ApiOperation("分页显示推荐商品")
    @GetMapping("/goods/recommend")
    public ResultWrapper pageRecommendGoods(@ApiParam("用户ID") @RequestHeader(required = false) String userId,
                                            @ApiParam("分页表单") PageForm pageForm) {
        PageVO<GoodsBriefDTO> goodsBriefDTOPageVO = recommendService.pageRecommendGoods(userId, pageForm);
        return ResultWrapper.success(goodsBriefDTOPageVO);
    }

    @ApiOperation("分页显示推荐手记")
    @GetMapping("/notes/recommend")
    public ResultWrapper pageRecommendNotes(@ApiParam("用户ID") @RequestHeader(required = false) String userId,
                                            @ApiParam("分页表单") PageForm pageForm) {
        PageVO<NotesBriefDTO> notesBriefDTOPageVO = recommendService.pageRecommendNotes(userId, pageForm);
        return ResultWrapper.success(notesBriefDTOPageVO);
    }

    @ApiOperation("分页显示推荐店铺")
    @GetMapping("/shop/recommend")
    public ResultWrapper pageRecommendShop(@ApiParam("用户ID") @RequestHeader(required = false) String userId,
                                           @ApiParam("分页表单") PageForm pageForm,
                                           @ApiParam("每个店铺展示商品数") @RequestParam Integer previewGoodsNum) {
        PageVO<ShopBriefDTO> shopBriefDTOPageVO = recommendService.pageRecommendShop(userId, pageForm, previewGoodsNum);
        return ResultWrapper.success(shopBriefDTOPageVO);
    }

    @ApiOperation("分页显示限时特惠")
    @GetMapping("/goods/promotion")
    public ResultWrapper pagePromotionGoods(@ApiParam("分页表单") PageForm pageForm) {
        return ResultWrapper.success(goodsService.listSaleGoods(pageForm));
    }

    @ApiOperation("内容管理系统接口")
    @GetMapping("/{cmsSiteId}/{type}")
    public ResultWrapper<List<CmsContentVO>> getCmsInfo(@ApiParam("所需内容页面Id") @PathVariable String cmsSiteId,
                                                        @ApiParam("种类") @PathVariable String type) {
        List<CmsContent> cmsContentList = cmsContentService.listCms(cmsSiteId, type);
        return ResultWrapper.success(BeanUtil.transList(cmsContentList, CmsContentVO.class));
    }

}
