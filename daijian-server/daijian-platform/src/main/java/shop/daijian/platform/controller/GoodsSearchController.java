package shop.daijian.platform.controller;


import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import shop.daijian.common.dto.GoodsBriefDTO;
import shop.daijian.common.form.PageForm;
import shop.daijian.common.form.QueryForm;
import shop.daijian.common.support.BaseController;
import shop.daijian.common.util.BeanUtil;
import shop.daijian.common.vo.PageVO;
import shop.daijian.common.wrapper.ResultWrapper;
import shop.daijian.platform.entity.Goods;
import shop.daijian.platform.service.GoodsSearchService;

import javax.validation.Valid;
import java.util.List;

/**
 * @Author stronghwan
 * @Verison
 * @Date2019/8/5-15-24
 */
@RestController
@RequestMapping(value = "/search")
@Api(tags = "搜索商品")
public class GoodsSearchController extends BaseController {

    @Autowired
    private GoodsSearchService goodsSearchService;

    @ApiOperation("搜索")
    @GetMapping(value = "/goods")
    public ResultWrapper<PageVO<GoodsBriefDTO>> search(@ApiParam("搜索关键字") @RequestParam(name = "keyword") String keyword,
                                                 @Valid QueryForm queryForm,
                                                 PageForm pageForm,
                                                 BindingResult bindingResult){
        validateParams(bindingResult);
        PageVO<Goods> goodsPage = goodsSearchService.searchGoods(keyword, queryForm, pageForm);
        List<GoodsBriefDTO> goodsVOS = BeanUtil.transList(goodsPage.getContent(), GoodsBriefDTO.class);
        PageVO<GoodsBriefDTO> goodsVOPageVO = new PageVO<>();
        goodsVOPageVO.setContent(goodsVOS).setSize(goodsPage.getSize()).setTotal(goodsPage.getTotal());
        return ResultWrapper.success(goodsVOPageVO);
    }

    @ApiOperation("自动补全搜索")
    @GetMapping(value = "/goods/{prefix}")
    public ResultWrapper<List<String>> suggest(@PathVariable @ApiParam("搜索前缀") String prefix,
                                               @RequestParam(name = "size")@ApiParam("获取前缀补全数量") Integer size){
        return ResultWrapper.success(goodsSearchService.suggest(prefix,"goods",size));
    }

}
