package shop.daijian.platform.service;

import shop.daijian.common.dto.ShopSearchDTO;
import shop.daijian.common.form.PageForm;
import shop.daijian.common.form.QueryForm;
import shop.daijian.common.vo.PageVO;
import shop.daijian.platform.entity.Shop;


/**
 * 店铺接口
 * @Author stronghwan
 * @Verison
 * @Date2019/8/13-16-43
 */
public interface ShopSearchService extends BaseSerachService{

    /**
     * 导入店铺数据
     * @return
     */
    Shop buildShop(ShopSearchDTO shopSearchDTO);

    /**
     * 搜索商品
     * @param queryForm
     * @return
     */
    PageVO<Shop> searchShop(String keyword, QueryForm queryForm, PageForm pageForm);

    /**
     * 增加或修改数据
     * @param shopSearchDTO
     */
    void createDoc(ShopSearchDTO shopSearchDTO);

    /**
     *  删除数据
     * @param id goods_id
     */
    void deleteDoc(String id);


    /**
     * 更新店铺搜索补全库
     * @param shop
     * @return
     */
    boolean updateShopSuggest(Shop shop);
}
