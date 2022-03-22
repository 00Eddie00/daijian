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
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.query.FetchSourceFilter;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.stereotype.Service;
import shop.daijian.common.dto.GoodsSearchDTO;
import shop.daijian.common.form.PageForm;
import shop.daijian.common.form.QueryForm;
import shop.daijian.common.interfaces.CatService;
import shop.daijian.common.vo.PageVO;
import shop.daijian.platform.entity.Goods;
import shop.daijian.platform.entity.GoodsSuggest;
import shop.daijian.platform.repository.GoodsRepository;
import shop.daijian.platform.repository.ShopRepository;
import shop.daijian.platform.service.GoodsSearchService;
import shop.daijian.platform.units.RegularUnits;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *  商品搜索实现类
 * @Author stronghwan
 * @Verison
 * @Date2019/8/5-10-06
 */
@Service
@Slf4j
public class GoodsSearchServiceImpl extends BaseSearchServiceImpl implements GoodsSearchService {

    @Autowired
    private GoodsRepository goodsRepository;

    @Autowired
    private ElasticsearchTemplate elasticsearchTemplate;

    @Reference
    private CatService catService;

    @Autowired
    private ShopRepository shopRepository;

    @Autowired
    private TransportClient transportClient;

    @Override
    public Goods buildGoods(GoodsSearchDTO goodsInfo) {
        Goods goods = new Goods();
        String category = catService.getCatKeyword(goodsInfo.getCatBackId());
        goods.setGoodsId(goodsInfo.getGoodsId())
                .setName(goodsInfo.getName())
                .setAvatarUrl(goodsInfo.getAvatarUrl())
                .setCategoryId(goodsInfo.getCatBackId())
                .setKeyword(goodsInfo.getName() + "," + goodsInfo.getOriginRegion() + "," + category)
                .setCategory(category)
                .setUnitPrice(goodsInfo.getUnitPrice())
                .setShopName(goodsInfo.getShopName())
                .setOriginPrice(goodsInfo.getOriginPrice())
                .setSpecification(goodsInfo.getSpecification())
                .setFavorableRate(goodsInfo.getFavorableRate())
                .setOriginRegion(goodsInfo.getOriginRegion())
                .setMonthlySales(goodsInfo.getMonthlySales())
                .setCreateTime(new Date());
       return goods;
    }


    @Override
    public PageVO<Goods> searchGoods(String keyword, QueryForm searchRequestForm, PageForm pageForm) {
        keyword = checkKeyword(keyword);
        NativeSearchQueryBuilder queryBuilder = new NativeSearchQueryBuilder();
        queryBuilder.withSourceFilter(
                new FetchSourceFilter(new String[]{"goodsId","name","avatarUrl","unitPrice","originPrice","monthlySales","shopName","specification"},null));
        if (QueryForm.useDefaultSort(searchRequestForm)){
            queryBuilder.withSort(SortBuilders.fieldSort("monthlySales").order(SortOrder.DESC));
            queryBuilder.withSort(SortBuilders.fieldSort("favorableRate").order(SortOrder.DESC));
        }
        sort(keyword,searchRequestForm,queryBuilder,pageForm);
        Page<Goods> search = goodsRepository.search(queryBuilder.build());
        PageVO<Goods> goodsPageVO = getPageVO(search);
        return goodsPageVO;
    }

    @Override
    public void createDoc(GoodsSearchDTO goodsInfo) {
        Goods goods = buildGoods(goodsInfo);
        if (!updateGoodsSuggest(goods)){
            log.debug("【添加或者修改商品没有分词】,goodsId={}",goods.getGoodsId());
        }
        goodsRepository.save(goods);
    }

    @Override
    public void deleteDoc(String id) {
        goodsRepository.deleteById(id);
    }

    @Override
    public boolean updateGoodsSuggest(Goods goods) {
        AnalyzeRequestBuilder requestBuilder = new AnalyzeRequestBuilder(
                transportClient, AnalyzeAction.INSTANCE, "goods", goods.getKeyword());
        requestBuilder.setAnalyzer("ik_smart");
        AnalyzeResponse analyzeTokens = requestBuilder.get();
        List<AnalyzeResponse.AnalyzeToken> tokens = analyzeTokens.getTokens();
        if (tokens == null) {
            log.info("【分词出现错误】" + goods.getGoodsId());
            return false;
        }
        List<GoodsSuggest> suggests = new ArrayList<>();
        for (AnalyzeResponse.AnalyzeToken token : tokens) {
            if ("<NUM>".equals(token.getType()) || token.getTerm().length() < 2 || RegularUnits.filterNumAndLer(token.getTerm())) {
                continue;
            }
            GoodsSuggest goodsSuggestText = new GoodsSuggest();
            goodsSuggestText.setInput(token.getTerm());
            suggests.add(goodsSuggestText);
        }
        // keyword类型不可分词
        GoodsSuggest originRegionSuggestText = new GoodsSuggest();
        originRegionSuggestText.setInput(goods.getOriginRegion());
        suggests.add(originRegionSuggestText);
        goods.setSuggests(suggests);
        return true;
    }
}
