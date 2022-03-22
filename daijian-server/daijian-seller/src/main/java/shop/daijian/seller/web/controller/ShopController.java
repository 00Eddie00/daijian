package shop.daijian.seller.web.controller;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import shop.daijian.common.dto.ActionTraceDTO;
import shop.daijian.common.dto.ShopBriefDTO;
import shop.daijian.common.enums.ActionTypeEnum;
import shop.daijian.common.enums.BaseStatusEnum;
import shop.daijian.common.exception.BizException;
import shop.daijian.common.form.PageForm;
import shop.daijian.common.interfaces.ActionService;
import shop.daijian.common.interfaces.ShopService;
import shop.daijian.common.support.BaseController;
import shop.daijian.common.util.BeanUtil;
import shop.daijian.common.vo.PageVO;
import shop.daijian.common.wrapper.ResultWrapper;
import shop.daijian.seller.entity.ShopInfo;
import shop.daijian.seller.enums.ShopStatusEnum;
import shop.daijian.seller.service.ShopInfoService;
import shop.daijian.seller.vo.ShopDetailVO;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;


/**
 * <p>
 * 店铺信息 前端控制器
 * </p>
 *
 * @author hsz
 * @author liuxin
 * @since 2019-08-04
 */
@RestController
@Api(tags = "店铺接口")
@RequestMapping
public class ShopController extends BaseController {

    @Autowired
    private ShopInfoService shopInfoService;

    @Autowired
    private ShopService shopService;

    @Reference
    private ActionService actionService;

    @ApiOperation("获取店铺详细信息")
    @GetMapping("/home/{shopId}")
    public ResultWrapper getShopHome(@RequestHeader(required = false) String userId, @ApiParam("店铺ID") @PathVariable String shopId) {
        ShopInfo shopInfo = shopInfoService.getById(shopId);
        if (shopInfo == null) {
            throw new BizException(ShopStatusEnum.SHOP_NOT_EXIST);
        }
        // 转换类型
        ShopDetailVO shopDetailVO = BeanUtil.transObj(shopInfo, ShopDetailVO.class);
        // 若用户登录
        if (userId != null) {
            // 是否已收藏
            Boolean favorite = actionService.existActionTrace(userId, shopId, ActionTypeEnum.SHOP_FAVORITE);
            shopDetailVO.setFavorite(favorite);
        }
        return ResultWrapper.success(shopDetailVO);
    }

    @ApiOperation("收藏店铺")
    @PostMapping("post/favorite/{shopId}")
    public ResultWrapper follow(@RequestHeader String userId, @ApiParam("店铺id") @PathVariable String shopId) {
        // 判断店铺是否存在
        ShopInfo shopInfo = shopInfoService.getById(shopId);
        if (shopInfo == null) {
            throw new BizException(ShopStatusEnum.SHOP_NOT_EXIST);
        }
        // 新增收藏行为
        Boolean success = actionService.saveActionTrace(userId, shopId, ActionTypeEnum.SHOP_FAVORITE, false);
        if (success) {
            // 增加店铺粉丝数 TODO ADD LOCK
            UpdateWrapper<ShopInfo> wrapper = new UpdateWrapper<>();
            wrapper.eq("shop_id", shopId).setSql("fans_num = fans_num + 1");
            shopInfoService.update(wrapper);
        }
        return ResultWrapper.success();
    }

    @ApiOperation("分页获取用户收藏的店铺")
    @GetMapping("get/favorite")
    public ResultWrapper listUserFavorite(@RequestHeader String userId, @ApiParam("预览商品个数") @RequestParam Integer previewGoodsNum, @Valid PageForm pageForm, BindingResult bindingResult) {
        validateParams(bindingResult);
        PageVO<ActionTraceDTO> actionTraceDTOPageVO = actionService.pageActionTrace(userId, ActionTypeEnum.SHOP_FAVORITE, pageForm, null);
        // 无收藏的店铺
        if (actionTraceDTOPageVO.isEmpty()) {
            return ResultWrapper.success(actionTraceDTOPageVO);
        }
        List<String> shopIdList = actionTraceDTOPageVO.getContent().stream().map(ActionTraceDTO::getTarget).collect(Collectors.toList());
        List<ShopBriefDTO> shopBriefDTOList = shopService.listByIdList(shopIdList, previewGoodsNum);
        return ResultWrapper.success(actionTraceDTOPageVO.replaceContent(shopBriefDTOList));
    }

    @ApiOperation("根据店铺ID列表取消收藏")
    @DeleteMapping("delete/favorite")
    public ResultWrapper removeShop(@RequestHeader String userId, @RequestBody List<String> shopIdList) {
        // 删除收藏行为
        Integer affectNum = actionService.remove(userId, shopIdList, ActionTypeEnum.SHOP_FAVORITE);
        if (affectNum != shopIdList.size()) {
            // TODO 事务回滚
            throw new BizException(BaseStatusEnum.NO_PERMISSION);
        }
        // 减少店铺粉丝数 TODO ADD LOCK
        UpdateWrapper<ShopInfo> wrapper = new UpdateWrapper<>();
        wrapper.setSql("fans_num = fans_num - 1").in("shop_id", shopIdList);
        shopInfoService.update(wrapper);
        return ResultWrapper.success();
    }

}
