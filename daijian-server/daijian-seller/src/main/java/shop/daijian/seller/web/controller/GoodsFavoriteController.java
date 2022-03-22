package shop.daijian.seller.web.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import shop.daijian.common.dto.ActionTraceDTO;
import shop.daijian.common.dto.GoodsBriefDTO;
import shop.daijian.common.enums.ActionTypeEnum;
import shop.daijian.common.exception.BizException;
import shop.daijian.common.form.PageForm;
import shop.daijian.common.form.QueryForm;
import shop.daijian.common.interfaces.ActionService;
import shop.daijian.common.interfaces.GoodsService;
import shop.daijian.common.support.BaseController;
import shop.daijian.common.vo.PageVO;
import shop.daijian.common.wrapper.ResultWrapper;
import shop.daijian.seller.entity.GoodsInfo;
import shop.daijian.seller.enums.GoodsStatusEnum;
import shop.daijian.seller.service.GoodsInfoService;

import javax.validation.Valid;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import static shop.daijian.common.util.BeanUtil.transList;

/**
 * @author liuxin
 * @since 2019/8/19 10:00
 */
@Slf4j
@Api(tags = {"商品收藏接口"})
@RestController
@RequestMapping("/favorite")
public class GoodsFavoriteController extends BaseController {

    @Reference
    private ActionService actionService;

    @Autowired
    GoodsInfoService goodsInfoService;

    @ApiOperation("收藏商品")
    @PostMapping("/{goodsId}")
    public ResultWrapper favorite(@RequestHeader String userId, @ApiParam("商品id") @PathVariable String goodsId) {
        // 判断商品是否存在
        GoodsInfo goodsInfo = goodsInfoService.getById(goodsId);
        if (goodsInfo == null) {
            throw new BizException(GoodsStatusEnum.NOT_EXIST);
        }
        // 新增收藏行为
        actionService.saveActionTrace(userId, goodsId, ActionTypeEnum.GOODS_FAVORITE, false);
        return ResultWrapper.success();
    }

    @ApiOperation("分页获取用户收藏商品")
    @GetMapping
    public ResultWrapper getUserFavoriteList(@RequestHeader String userId, @Valid PageForm pageForm, BindingResult bindingResult) {
        validateParams(bindingResult);
        PageVO<ActionTraceDTO> actionTraceDTOPageVO = actionService.pageActionTrace(userId, ActionTypeEnum.GOODS_FAVORITE, pageForm, null);
        // 无收藏的商品
        if (actionTraceDTOPageVO.isEmpty()) {
            return ResultWrapper.success(actionTraceDTOPageVO);
        }
        List<String> goodsIdList = actionTraceDTOPageVO.getContent().stream().map(ActionTraceDTO::getTarget).collect(Collectors.toList());
        Collection<GoodsInfo> goodsInfos = goodsInfoService.listByIds(goodsIdList);
        List<GoodsBriefDTO> goodsBriefDTOS = transList(goodsInfos, GoodsBriefDTO.class);
        return ResultWrapper.success(actionTraceDTOPageVO.replaceContent(goodsBriefDTOS));
    }

    @ApiOperation("根据商品idList取消收藏")
    @DeleteMapping
    public ResultWrapper removeUserFavorite(@RequestHeader String userId, @RequestBody List<String> goodsIdList) {
        // 删除收藏行为
        actionService.remove(userId, goodsIdList, ActionTypeEnum.GOODS_FAVORITE);
        return ResultWrapper.success();
    }
}



