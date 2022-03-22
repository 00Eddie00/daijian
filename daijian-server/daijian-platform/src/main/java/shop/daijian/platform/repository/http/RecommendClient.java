package shop.daijian.platform.repository.http;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import shop.daijian.common.form.PageForm;
import shop.daijian.common.util.HttpUtils;
import shop.daijian.platform.enums.RecommendEnum;
import shop.daijian.platform.wrapper.IdListWrapper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author qiyubing, hanshizhou
 * @since 2019-08-23
 */
@Component
public class RecommendClient {

    @Value("${client.recommend-goods-url}")
    private String recommendGoodsUrl;

    @Value("${client.recommend-shop-url}")
    private String recommendShopUrl;

    @Value("${client.recommend-notes-url}")
    private String recommendNotesUrl;

    public List<String> pageRecommendIdList(String userId, PageForm pageForm, RecommendEnum recommendEnum) {
        Map<String, Object> parameterMap = new HashMap<>();
        parameterMap.put("userId", userId);
        parameterMap.put("page", pageForm.getPage());
        parameterMap.put("size", pageForm.getSize());
        String url;
        switch (recommendEnum){
            case GOODS_RECOMMEND: url = recommendGoodsUrl; break;
            case SHOP_RECOMMEND: url = recommendShopUrl; break;
            case NOTES_RECOMMEND: url = recommendNotesUrl; break;
            default: url = null;
        }
        IdListWrapper idListWrapper = HttpUtils.get(url, parameterMap, IdListWrapper.class);
        if (idListWrapper == null) {
            return new ArrayList<>();
        }
        return idListWrapper.getIdList();
    }

}
