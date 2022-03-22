package shop.daijian.common.interfaces;

import shop.daijian.common.dto.GoodsBriefDTO;
import shop.daijian.common.dto.GoodsSearchDTO;
import shop.daijian.common.form.PageForm;
import shop.daijian.common.form.QueryForm;
import shop.daijian.common.vo.PageVO;

import java.util.List;

/**
 * @author liuxin
 * @date 2019/8/4 20:26
 */
public interface GoodsService {

    /**
     * 通过商品ID获取商品简介
     *
     * @param goodId 商品ID
     * @return 商品
     */
    GoodsBriefDTO getGoodsBriefById(String goodId);

    /**
     * 分页获取商品搜索信息
     */
    PageVO<GoodsSearchDTO> pageGoodsSearch(PageForm pageForm);

    /**
     * 获取商品搜索信息
     */
    GoodsSearchDTO getGoodsSearchById(String goodsId);

    /**
     * 通过商品ID列表查询商品信息列表（保证顺序，但不保证缺失）
     */
    List<GoodsBriefDTO> listByIdList(List<String> goodsIdList);

    /**
     * 减少库存，如果库存不足则抛异常
     *
     * @param goodsId   商品id
     * @param reduceNum 减少个数
     */
    void reduceStock(String goodsId, Integer reduceNum);

    /**
     * 根据后台分类id查询商品简介
     */
    List<GoodsBriefDTO> listByCatBackId(String catBackId);

    /**
     * 增加总销量
     *
     * @param goodsId 商品ID
     * @param addNum  增加的数量
     */
    void addTotalSales(String goodsId, Integer addNum);

    /**
     * 获取店铺商品销量排行榜
     */
    PageVO<GoodsBriefDTO> pageShopGoods(String shopId, PageForm pageForm, QueryForm queryForm);

    /**
     * 展示特惠商品
     *
     * @param pageForm
     * @return
     */
    PageVO<GoodsBriefDTO> listSaleGoods(PageForm pageForm);
}
