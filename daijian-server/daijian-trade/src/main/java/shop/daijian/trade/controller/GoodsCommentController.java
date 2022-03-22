package shop.daijian.trade.controller;


import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import shop.daijian.common.dto.CommentDTO;
import shop.daijian.common.enums.BaseStatusEnum;
import shop.daijian.common.enums.CommentTypeEnum;
import shop.daijian.common.exception.BizException;
import shop.daijian.common.form.PageForm;
import shop.daijian.common.form.QueryForm;
import shop.daijian.common.interfaces.CommentService;
import shop.daijian.common.support.BaseController;
import shop.daijian.common.util.BeanUtil;
import shop.daijian.common.vo.PageVO;
import shop.daijian.common.wrapper.ResultWrapper;
import shop.daijian.trade.entity.OrderGoods;
import shop.daijian.trade.enums.OrderStateEnum;
import shop.daijian.trade.enums.TradeStatusEnum;
import shop.daijian.trade.form.GoodsCommentForm;
import shop.daijian.trade.service.OrderGoodsService;
import shop.daijian.trade.vo.GoodsCommentVO;

import javax.validation.Valid;
import java.util.List;


/**
 * <p>
 * 商品评价 前端控制器
 * </p>
 *
 * @author liuxin
 * @since 2019-08-13
 */
@Api(tags = "商品评价接口")
@RestController
@RequestMapping("/comment")
public class GoodsCommentController extends BaseController {

    @Autowired
    private OrderGoodsService orderGoodsService;

    @Reference
    private CommentService commentService;

    @ApiOperation("分页获取商品的所有评论")
    @GetMapping("/{goodsId}")
    public ResultWrapper<PageVO<GoodsCommentVO>> pageComment(@PathVariable String goodsId,
                                                             @ApiParam("最低星级（闭区间）") @RequestParam Integer low,
                                                             @ApiParam("最高星级（闭区间）") @RequestParam Integer high,
                                                             @Valid QueryForm queryForm, @Valid PageForm pageForm, BindingResult bindingResult) {
        validateParams(bindingResult);
        PageVO<CommentDTO> commentDTOPageVO = commentService.pageComment(goodsId, CommentTypeEnum.GOODS, pageForm, queryForm, low, high);
        List<GoodsCommentVO> goodsCommentVOS = BeanUtil.transList(commentDTOPageVO.getContent(), GoodsCommentVO.class);
        return ResultWrapper.success(commentDTOPageVO.replaceContent(goodsCommentVOS));
    }

    @ApiOperation("添加评论")
    @PostMapping
    public ResultWrapper addComment(@RequestHeader String userId, @Valid @RequestBody GoodsCommentForm goodsCommentForm, BindingResult bindingResult) {
        validateParams(bindingResult);
        //   判断下单商品的状态，如果用户以确定收货，才可评论
        OrderGoods orderGoods = orderGoodsService.getById(goodsCommentForm.getOrderGoodsId());
        if (orderGoods.getState() == OrderStateEnum.INITIALIZE) {
            throw new BizException(TradeStatusEnum.ORDER_NOT_EXIST);
        }
        if (!orderGoods.getState().equals(OrderStateEnum.WAIT_COMMENT)) {
            throw new BizException(TradeStatusEnum.STATE_UN_SUPPORT);
        }
        if (!orderGoods.getUserId().equals(userId)) {
            throw new BizException(BaseStatusEnum.NO_PERMISSION);
        }
        // 构造评论DTO
        CommentDTO commentDTO = BeanUtil.transObj(goodsCommentForm, CommentDTO.class);
        commentDTO.setUserId(userId)
                .setTargetId(orderGoods.getGoodsId())
                .setUniqueId(goodsCommentForm.getOrderGoodsId())
                .setCommentTypeEnum(CommentTypeEnum.GOODS);
        // 保存评论
        commentService.saveComment(commentDTO, false);
        //  设置订单商品状态为已评论
        UpdateWrapper<OrderGoods> wrapper = new UpdateWrapper<>();
        wrapper.eq("order_goods_id", goodsCommentForm.getOrderGoodsId()).set("state", OrderStateEnum.COMMENTED);
        orderGoodsService.update(wrapper);
        return ResultWrapper.success();
    }

}




