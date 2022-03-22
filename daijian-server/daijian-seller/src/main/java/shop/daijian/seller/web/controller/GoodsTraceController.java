package shop.daijian.seller.web.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import shop.daijian.common.dto.ActionTraceDTO;
import shop.daijian.common.dto.GoodsBriefDTO;
import shop.daijian.common.enums.ActionTypeEnum;
import shop.daijian.common.form.PageForm;
import shop.daijian.common.form.QueryForm;
import shop.daijian.common.interfaces.ActionService;
import shop.daijian.common.interfaces.GoodsService;
import shop.daijian.common.support.BaseController;
import shop.daijian.common.vo.PageVO;
import shop.daijian.common.wrapper.ResultWrapper;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

import static shop.daijian.common.util.BeanUtil.transList;


/**
 * <p>
 * 商品浏览记录 前端控制器
 * </p>
 *
 * @author liuxin
 * @since 2019-08-04
 */
@Api(tags = {"商品浏览历史记录接口"})
@RestController
@RequestMapping("/trace")
public class GoodsTraceController extends BaseController {

    @Autowired
    private GoodsService goodsService;

    @Reference
    private ActionService actionService;

    @ApiOperation("分页获取用户商品浏览历史(时间倒叙)")
    @GetMapping
    public ResultWrapper getGoodsTraceList(@RequestHeader String userId, @Valid PageForm pageForm, BindingResult bindingResult) {
        validateParams(bindingResult);
        QueryForm queryForm = new QueryForm("update_time", "desc");
        PageVO<ActionTraceDTO> actionTraceDTOPageVO = actionService.pageActionTrace(userId, ActionTypeEnum.GOODS_VIEW, pageForm, queryForm);
        // 无浏览的商品
        if (actionTraceDTOPageVO.isEmpty()) {
            return ResultWrapper.success(actionTraceDTOPageVO);
        }
        List<String> goodsIdList = actionTraceDTOPageVO.getContent().stream().map(ActionTraceDTO::getTarget).collect(Collectors.toList());
        List<GoodsBriefDTO> goodsBrief = goodsService.listByIdList(goodsIdList);
        List<GoodsBriefDTO> goodsBriefDTOS = transList(goodsBrief, GoodsBriefDTO.class);
        return ResultWrapper.success(actionTraceDTOPageVO.replaceContent(goodsBriefDTOS));
    }

    @ApiOperation("清空用户商品浏览历史")
    @DeleteMapping("/all")
    public ResultWrapper clearGoodsTrace(@RequestHeader String userId) {
        actionService.removeAll(userId, ActionTypeEnum.GOODS_VIEW);
        return ResultWrapper.success();
    }

    @ApiOperation("根据商品idList删除浏览历史")
    @DeleteMapping
    public ResultWrapper removeGoodsTrace(@RequestHeader String userId, @RequestBody List<String> goodsIdList) {
        actionService.remove(userId, goodsIdList, ActionTypeEnum.GOODS_VIEW);
        return ResultWrapper.success();
    }
}
