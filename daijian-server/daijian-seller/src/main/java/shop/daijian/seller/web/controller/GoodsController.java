package shop.daijian.seller.web.controller;


import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import shop.daijian.common.dto.ShopBriefDTO;
import shop.daijian.common.enums.ActionTypeEnum;
import shop.daijian.common.exception.BizException;
import shop.daijian.common.form.PageForm;
import shop.daijian.common.form.QueryForm;
import shop.daijian.common.interfaces.ActionService;
import shop.daijian.common.interfaces.GoodsService;
import shop.daijian.common.interfaces.ShopService;
import shop.daijian.common.support.BaseController;
import shop.daijian.common.util.JsonUtils;
import shop.daijian.common.wrapper.ResultWrapper;

import shop.daijian.seller.entity.GoodsInfo;
import shop.daijian.seller.enums.GoodsStatusEnum;
import shop.daijian.seller.service.GoodsInfoService;
import shop.daijian.seller.vo.GoodsDetailVO;

import javax.validation.Valid;

import static shop.daijian.common.util.BeanUtil.transObj;

/**
 * <p>
 * 商品信息 前端控制器
 * </p>
 *
 * @author liuxin
 * @since 2019-08-04
 */
@Slf4j
@Api(tags = {"商品接口"})
@RestController
@RequestMapping
public class GoodsController extends BaseController {

    @Autowired
    private GoodsInfoService goodsInfoService;

    @Autowired
    private GoodsService goodsService;

    @Reference
    private ActionService actionService;

    @Reference
    private ShopService shopService;

    @ApiOperation("通过商品id获取商品详情")
    @GetMapping("/detail/{goodsId}")
    public ResultWrapper<GoodsDetailVO> getGoodsDetail(@RequestHeader(required = false) String userId, @ApiParam("商品id") @PathVariable String goodsId) {
        // 根据商品ID得到对应的商品信息
        GoodsInfo goodsInfo = goodsInfoService.getById(goodsId);
        if (goodsInfo == null) {
            throw new BizException(GoodsStatusEnum.NOT_EXIST);
        }
        // 转换类型
        GoodsDetailVO goodsDetailVO = transObj(goodsInfo, GoodsDetailVO.class);
        // 填充图册
        goodsDetailVO.setImageUrlList(JsonUtils.toList(goodsInfo.getImageUrlJson(), String.class));
        // 填充商品介绍
        goodsDetailVO.setContentUrlList(JsonUtils.toList(goodsInfo.getContentUrlJson(), String.class));
        // 查询店铺信息
        ShopBriefDTO shopBriefDTO = shopService.getShopBrief(goodsInfo.getShopId());
        //若用户登录
        if (userId != null) {
            // 商品是否已收藏
            Boolean favorite = actionService.existActionTrace(userId, goodsId, ActionTypeEnum.GOODS_FAVORITE);
            goodsDetailVO.setFavorite(favorite);
            // 店铺是否已收藏
            Boolean shopFavorite = actionService.existActionTrace(userId, goodsInfo.getShopId(), ActionTypeEnum.SHOP_FAVORITE);
            shopBriefDTO.setFavorite(shopFavorite);
            // 增加浏览历史
            actionService.saveActionTrace(userId, goodsId, ActionTypeEnum.GOODS_VIEW, false);
        }
        // 填充店铺信息
        goodsDetailVO.setShopBriefDTO(shopBriefDTO);
        return ResultWrapper.success(goodsDetailVO);
    }

    @ApiOperation("分页获取店铺商品列表")
    @GetMapping("/brief/shop/{shopId}")
    public ResultWrapper listBriefByShopId(
            @PathVariable String shopId, @Valid PageForm pageForm, @Valid QueryForm queryForm, BindingResult bindingResult) {
        validateParams(bindingResult);
        return ResultWrapper.success(goodsService.pageShopGoods(shopId, pageForm, queryForm));
    }

}



