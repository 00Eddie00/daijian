package shop.daijian.platform.service.impl;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.Reference;
import org.elasticsearch.action.admin.indices.analyze.AnalyzeAction;
import org.elasticsearch.action.admin.indices.analyze.AnalyzeRequestBuilder;
import org.elasticsearch.action.admin.indices.analyze.AnalyzeResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.elasticsearch.core.query.FetchSourceFilter;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.stereotype.Service;
import shop.daijian.common.dto.ShopSearchDTO;
import shop.daijian.common.form.PageForm;
import shop.daijian.common.form.QueryForm;
import shop.daijian.common.interfaces.GoodsService;
import shop.daijian.common.interfaces.ShopService;
import shop.daijian.common.vo.PageVO;
import shop.daijian.platform.entity.Shop;
import shop.daijian.platform.entity.ShopSuggest;
import shop.daijian.platform.repository.ShopRepository;
import shop.daijian.platform.service.BaseSerachService;
import shop.daijian.platform.service.ShopSearchService;

import java.util.ArrayList;
import java.util.List;

/**
 * 店铺搜索实现类
 * @Author stronghwan
 * @Verison
 * @Date2019/8/13-15-24
 */
@Service
@Slf4j
public class ShopSearchServiceImpl extends BaseSearchServiceImpl implements ShopSearchService, BaseSerachService {

    @Autowired
    private ShopRepository shopRepository;

    @Autowired
    private TransportClient transportClient;

    @Reference
    private ShopService shopService;

    @Reference
    private GoodsService goodsService;

    @Override
    public Shop buildShop(ShopSearchDTO shopSearchDTO) {
        Shop shop = new Shop();
        shop.setShopId(shopSearchDTO.getShopId())
                .setKeyword(shopSearchDTO.getName() + "," +shopSearchDTO.getRegion())
                .setName(shopSearchDTO.getName())
                .setAvatarUrl(shopSearchDTO.getAvatarUrl())
                .setMonthlySales(shopSearchDTO.getMonthlySales())
                .setOriginRegion(shopSearchDTO.getRegion())
                .setCredit(shopSearchDTO.getCredit());
        return shop;
    }

    @Override
    public PageVO<Shop> searchShop(String keyword, QueryForm queryForm, PageForm pageForm) {
        keyword = checkKeyword(keyword);
        NativeSearchQueryBuilder queryBuilder = new NativeSearchQueryBuilder();
        queryBuilder.withSourceFilter(
                new FetchSourceFilter(new String[]{"shopId","shopName","avatarUrl","credit"},null));
        if (QueryForm.useDefaultSort(queryForm)){
            queryBuilder.withSort(SortBuilders.fieldSort("monthlySales").order(SortOrder.DESC));
            queryBuilder.withSort(SortBuilders.fieldSort("credit").order(SortOrder.DESC));
        }
        sort(keyword,queryForm,queryBuilder,pageForm);
        Page<Shop> search = shopRepository.search(queryBuilder.build());
        PageVO<Shop> shopPageVO = getPageVO(search);
        return shopPageVO;
    }

    @Override
    public void createDoc(ShopSearchDTO shopSearchDTO) {
        Shop shop = buildShop(shopSearchDTO);
        if (!updateShopSuggest(shop)){
            log.debug("【添加或者修改商品没有分词】,goodsId={}",shop.getShopId());
        }
        shopRepository.save(shop);
    }

    @Override
    public void deleteDoc(String id) {
        shopRepository.deleteById(id);
    }

    @Override
    public boolean updateShopSuggest(Shop shop) {
        AnalyzeRequestBuilder requestBuilder = new AnalyzeRequestBuilder(
                transportClient, AnalyzeAction.INSTANCE, "shop", shop.getKeyword(),shop.getOriginRegion());
        requestBuilder.setAnalyzer("ik_smart");
        AnalyzeResponse analyzeTokens = requestBuilder.get();
        List<AnalyzeResponse.AnalyzeToken> tokens = analyzeTokens.getTokens();
        if (tokens == null) {
            log.info("【分词出现错误】" + shop.getShopId());
            return false;
        }
        List<ShopSuggest> suggests = new ArrayList<>();
        for (AnalyzeResponse.AnalyzeToken token : tokens) {
            if ("<NUM>".equals(token.getType()) || token.getTerm().length() < 2) {
                continue;
            }
            ShopSuggest shopSuggest = new ShopSuggest();
            shopSuggest.setInput(token.getTerm());
            suggests.add(shopSuggest);
        }
        ShopSuggest shopSuggestName = new ShopSuggest();
        shopSuggestName.setInput(shop.getName());
        suggests.add(shopSuggestName);
        shop.setSuggests(suggests);
        return true;
    }
}
