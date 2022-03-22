package shop.daijian.platform.service;


import shop.daijian.common.dto.GoodsBriefDTO;
import shop.daijian.common.dto.NotesBriefDTO;
import shop.daijian.common.dto.ShopBriefDTO;
import shop.daijian.common.form.PageForm;
import shop.daijian.common.vo.PageVO;

/**
 *  首页推荐
 *
 * @author qiyubing, hanshizhou
 * @since 2019-08-23
 */
public interface RecommendService {
    /**
     * 分页获取推荐的商品信息
     *
     * @param userId
     * @param pageForm
     * @return PageVO<GoodsBriefDTO>
     */
    PageVO<GoodsBriefDTO> pageRecommendGoods(String userId, PageForm pageForm);

    /**
     * 分页获取推荐的店铺信息
     *
     * @param userId
     * @param pageForm
     * @return PageVO<ShopBriefDTO>
     */
    PageVO<ShopBriefDTO> pageRecommendShop(String userId, PageForm pageForm, Integer previewGoodsNum);

    /**
     * 分页获取推荐的手记信息
     *
     * @param userId
     * @param pageForm
     * @return PageVO<NotesBriefDTO>
     */
    PageVO<NotesBriefDTO> pageRecommendNotes(String userId, PageForm pageForm);

}
