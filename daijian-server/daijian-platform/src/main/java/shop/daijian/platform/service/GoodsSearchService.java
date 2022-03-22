package shop.daijian.platform.service;

import shop.daijian.common.dto.GoodsSearchDTO;
import shop.daijian.common.form.PageForm;
import shop.daijian.common.form.QueryForm;
import shop.daijian.common.vo.PageVO;
import shop.daijian.platform.entity.Goods;


/**
 * @Author stronghwan
 * @Verison
 * @Date2019/8/13-16-39
 */
public interface GoodsSearchService extends BaseSerachService {
    /**
     * 导入商品数据
     * @return
     */
    Goods buildGoods(GoodsSearchDTO goodsSearchDTO);

    /**
     * 搜索商品
     * @param queryForm
     * @return
     */
    PageVO<Goods> searchGoods(String keyword, QueryForm queryForm, PageForm pageForm);

    /**
     * 增加或修改数据
     * @param goodsSearchDTO
     */
    void createDoc(GoodsSearchDTO goodsSearchDTO);

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

}
