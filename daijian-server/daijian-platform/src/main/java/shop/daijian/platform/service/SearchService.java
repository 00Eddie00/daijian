package shop.daijian.platform.service;

import shop.daijian.common.dto.GoodsSearchDTO;
import shop.daijian.common.form.PageForm;
import shop.daijian.common.form.QueryForm;
import shop.daijian.common.vo.PageVO;
import shop.daijian.platform.entity.Goods;
import shop.daijian.platform.entity.Shop;

import java.util.List;

/**
 * @Author stronghwan
 * @Verison
 * @Date2019/8/5-10-04
 */
public interface SearchService {

    /**
     * 导入商品数据
     * @return
     */
    Goods buildGoods(GoodsSearchDTO goodsSearchDTO);

    /**
     * 导入店铺数据
     * @return
     */
    Shop buildShop(GoodsSearchDTO goodsSearchDTO);

    /**
     * 搜索商品
     * @param queryForm
     * @return
     */
    PageVO<Goods> searchGoods(String keyword, QueryForm queryForm, PageForm pageForm);

    /**
     * 搜索商品
     * @param queryForm
     * @return
     */
    PageVO<Shop> searchShop(String keyword, QueryForm queryForm, PageForm pageForm);

    /**
     * 增加或修改数据
     * @param goodsInfo
     */
    void createDoc(GoodsSearchDTO goodsInfo);

    /**
     *  删除数据
     * @param id goods_id
     */
    void deleteDoc(String id);

    /**
     * 更新商品搜索补全库
     * @param goods
     * @return
     */
    boolean updateGoodsSuggest(Goods goods);

    /**
     * 更新店铺搜索补全库
     * @param shop
     * @return
     */
    boolean updateShopSuggest(Shop shop);

    /**
     *  自动补全结果
     * @param prefix 搜索前缀
     * @return
     */
    List<String> suggest(String prefix, String index);
}
