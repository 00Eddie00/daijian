package shop.daijian.user.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import shop.daijian.common.support.BaseController;
import shop.daijian.user.dto.CartDTO;
import shop.daijian.user.form.GoodsPackForm;
import shop.daijian.user.repository.redis.CartTemplate;
import shop.daijian.common.dto.GoodsBriefDTO;
import shop.daijian.common.interfaces.GoodsService;
import shop.daijian.common.interfaces.ShopService;
import shop.daijian.common.wrapper.ResultWrapper;
import shop.daijian.user.service.CartService;
import shop.daijian.user.vo.CartGoodsVO;
import shop.daijian.user.vo.CartUnitVO;

import javax.validation.Valid;
import java.util.*;

/**
 * @author hanshizhou
 * @since 2019/08/16
 */
@Slf4j
@Api(tags = {"购物车接口"})
@RestController
@RequestMapping
public class CartController extends BaseController {

    @Autowired(required = false)
    private CartService cartService;

    @Autowired
    private CartTemplate cartTemplate;

    @Reference
    private GoodsService goodsService;

    @Reference
    private ShopService shopService;

    @ApiOperation("增加一个商品或者增加一个已有商品的数量")
    @PostMapping("/add")
    public ResultWrapper goodsPack(@RequestHeader String userId, @ApiParam("商品表单") @Valid @RequestBody GoodsPackForm goodsPackForm, BindingResult bindResult) {
        validateParams(bindResult);
        cartService.goodsPack(userId, goodsPackForm.getGoodsId(), goodsPackForm.getNum());
        return ResultWrapper.success();
    }

    @ApiOperation("减少某商品数量一个单位，若只剩一个则删除")
    @GetMapping("/reduceCart/{goodsId}")
    public ResultWrapper reduceGoodsNum(@RequestHeader String userId, @ApiParam("商品id") @PathVariable String goodsId) {
        cartService.reduceGoodsNum(userId, goodsId, 1);
        return ResultWrapper.success();
    }

    @ApiOperation("根据商品idList删除")
    @DeleteMapping("/deleteCart")
    public ResultWrapper deleteGoods(@RequestHeader String userId, @ApiParam("商品ID列表") @RequestBody List<String> goodsIdList) {
        cartService.deleteGoods(userId, goodsIdList);
        return ResultWrapper.success();
    }

    @ApiOperation("查询用户购物车信息")
    @GetMapping("/cart")
    public ResultWrapper getCart(@RequestHeader String userId) {
        //通过遍历所有商品填充用户购物车
        Map<String, CartUnitVO> cartGroup = new HashMap<>();
        CartDTO cartDTO = cartTemplate.get(userId);
        if (cartDTO == null) {
            return ResultWrapper.success(new ArrayList<>());
        } else {
            // 从购物车获取商品id列表
            List<String> goodsIdList = new ArrayList<>(cartDTO.keySet());
            List<GoodsBriefDTO> goodsBriefDTOList = goodsService.listByIdList(goodsIdList);
            // 遍历有效的商品列表
            goodsBriefDTOList.stream().filter(Objects::nonNull).forEach((goodsBriefDTO) -> {
                CartUnitVO cartUnitVO;
                String shopId = goodsBriefDTO.getShopId();
                List<CartGoodsVO> cartGoodsVOList;
                // 填充购物车商品信息
                CartGoodsVO cartGoodsVO = new CartGoodsVO();
                BeanUtils.copyProperties(goodsBriefDTO, cartGoodsVO);
                // 设置商品数量
                cartGoodsVO.setGoodsNum(cartDTO.get(goodsBriefDTO.getGoodsId()));
                if (cartGroup.get(shopId) == null) {
                    //若店铺在购物车中未创建，则新建店铺并添加商品
                    cartUnitVO = new CartUnitVO();
                    BeanUtils.copyProperties(goodsBriefDTO, cartUnitVO);
                    cartGoodsVOList = new ArrayList<>();
                    cartGoodsVOList.add(cartGoodsVO);
                } else {
                    // 若店铺在购物车中已创建，则添加商品到对应的店铺
                    cartUnitVO = cartGroup.get(shopId);
                    cartGoodsVOList = cartUnitVO.getCartGoodsVOList();
                    cartGoodsVOList.add(cartGoodsVO);
                }
                // 设置店铺下的商品
                cartUnitVO.setCartGoodsVOList(cartGoodsVOList);
                //填充购物车
                cartGroup.put(shopId, cartUnitVO);
            });
            return ResultWrapper.success(cartGroup.values());
        }
    }
}
