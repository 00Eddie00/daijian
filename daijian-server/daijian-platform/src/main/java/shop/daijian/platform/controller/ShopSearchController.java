package shop.daijian.platform.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import shop.daijian.common.dto.GoodsBriefDTO;
import shop.daijian.common.dto.ShopBriefDTO;
import shop.daijian.common.form.PageForm;
import shop.daijian.common.form.QueryForm;
import shop.daijian.common.interfaces.GoodsService;
import shop.daijian.common.support.BaseController;
import shop.daijian.common.util.BeanUtil;
import shop.daijian.common.vo.PageVO;
import shop.daijian.common.wrapper.ResultWrapper;
import shop.daijian.platform.entity.Shop;
import shop.daijian.platform.service.ShopSearchService;

import javax.validation.Valid;
import java.util.List;

/**
 * @Author stronghwan
 * @Verison
 * @Date2019/8/13-15-41
 */
@RestController
@RequestMapping(value = "/search")
@Api(tags = "搜索店铺")
public class ShopSearchController extends BaseController {

    @Reference
    private GoodsService goodsService;

    @Autowired
    private ShopSearchService shopSearchService;

    @ApiOperation("搜索")
    @GetMapping(value = "/shop")
    public ResultWrapper<PageVO<ShopBriefDTO>> search(@ApiParam("搜索关键字") @RequestParam(value = "keyword") String keyword,
                                                      @ApiParam("商品图片个数") @RequestParam(value = "num") Integer num,
                                                      @Valid QueryForm queryForm,
                                                      PageForm pageForm,
                                                      BindingResult bindingResult){
        validateParams(bindingResult);
        PageVO<Shop> shopPage = shopSearchService.searchShop(keyword, queryForm, pageForm);
        List<ShopBriefDTO> shopVOS = BeanUtil.transList(shopPage.getContent(), ShopBriefDTO.class);
        PageVO<ShopBriefDTO> shopVOPageVO = new PageVO<>();
        shopVOPageVO.setContent(shopVOS).setSize(shopPage.getSize()).setTotal(shopPage.getTotal());
        for (ShopBriefDTO shopVO : shopVOPageVO.getContent()) {
            PageForm pageFormGoods = new PageForm();
            pageFormGoods.setPage(1);
            pageFormGoods.setSize(num);
            PageVO<GoodsBriefDTO> goodsBriefDTOS = goodsService.pageShopGoods(shopVO.getShopId(), pageFormGoods, queryForm);
            shopVO.setGoodsBriefDTOList(goodsBriefDTOS.getContent());
        }

        return ResultWrapper.success(shopVOPageVO);
    }
    @ApiOperation("自动补全搜索")
    @GetMapping(value = "/shop/{prefix}")
    public ResultWrapper<List<String>> suggest(@PathVariable @ApiParam("搜索前缀") String prefix,
                                               @RequestParam(name = "size")@ApiParam("获取前缀补全数量") Integer size){

        return ResultWrapper.success(shopSearchService.suggest(prefix,"shop",size));
    }
}
