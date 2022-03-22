package shop.daijian.platform.service.impl;

import org.apache.dubbo.config.annotation.Reference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import shop.daijian.common.dto.GoodsBriefDTO;
import shop.daijian.common.dto.NotesBriefDTO;
import shop.daijian.common.dto.ShopBriefDTO;
import shop.daijian.common.form.PageForm;
import shop.daijian.common.interfaces.GoodsService;
import shop.daijian.common.interfaces.NotesService;
import shop.daijian.common.interfaces.ShopService;
import shop.daijian.common.vo.PageVO;
import shop.daijian.platform.repository.mybatis.CmsContentMapper;
import shop.daijian.platform.enums.RecommendEnum;
import shop.daijian.platform.repository.http.RecommendClient;
import shop.daijian.platform.service.RecommendService;

import java.util.List;

/**
 * @author qiyubing, hanshizhou
 * @since 2019-08-23
 */
@Service
public class RecommendServiceImpl implements RecommendService {

    @Autowired
    private RecommendClient recommendClient;

    @Autowired
    private CmsContentMapper cmsContentMapper;

    @Reference
    private GoodsService goodsService;

    @Reference
    private ShopService shopService;

    @Reference
    private NotesService notesService;

    @Override
    public PageVO<GoodsBriefDTO> pageRecommendGoods(String userId, PageForm pageForm) {
        List<String> goodsIdList = recommendClient.pageRecommendIdList(userId, pageForm, RecommendEnum.GOODS_RECOMMEND);
        List<GoodsBriefDTO> goodsBriefDTOList = goodsService.listByIdList(goodsIdList);
        return new PageVO<>(pageForm.getPage().longValue(), pageForm.getSize().longValue(), 0L, goodsBriefDTOList);
    }

    @Override
    public PageVO<ShopBriefDTO> pageRecommendShop(String userId, PageForm pageForm, Integer previewGoodsNum) {
        List<String> shopIdList = recommendClient.pageRecommendIdList(userId, pageForm, RecommendEnum.SHOP_RECOMMEND);
        List<ShopBriefDTO> shopBriefDTOList = shopService.listByIdList(shopIdList, previewGoodsNum);
        return new PageVO<>(pageForm.getPage().longValue(), pageForm.getSize().longValue(), 0L, shopBriefDTOList);
    }

    @Override
    public PageVO<NotesBriefDTO> pageRecommendNotes(String userId, PageForm pageForm) {
        List<String> notesIdList = recommendClient.pageRecommendIdList(userId, pageForm, RecommendEnum.NOTES_RECOMMEND);
        List<NotesBriefDTO> notesBriefDTOList = notesService.listByIdList(notesIdList);
        return new PageVO<>(pageForm.getPage().longValue(), pageForm.getSize().longValue(), 0L, notesBriefDTOList);
    }


}
