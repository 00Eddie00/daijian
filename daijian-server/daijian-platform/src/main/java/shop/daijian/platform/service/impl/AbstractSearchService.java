package shop.daijian.platform.service.impl;

import org.apache.commons.lang.StringUtils;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.index.query.Operator;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.elasticsearch.search.suggest.Suggest;
import org.elasticsearch.search.suggest.SuggestBuilder;
import org.elasticsearch.search.suggest.SuggestBuilders;
import org.elasticsearch.search.suggest.completion.CompletionSuggestion;
import org.elasticsearch.search.suggest.completion.CompletionSuggestionBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import shop.daijian.common.dto.GoodsSearchDTO;
import shop.daijian.common.form.PageForm;
import shop.daijian.common.form.QueryForm;
import shop.daijian.common.vo.PageVO;
import shop.daijian.platform.entity.Goods;
import shop.daijian.platform.entity.Shop;
import shop.daijian.platform.service.SearchService;
import shop.daijian.platform.units.TypeConversion;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @Author stronghwan
 * @Verison
 * @Date2019/8/13-15-15
 */
public abstract class AbstractSearchService implements SearchService {

    @Autowired
    TransportClient transportClient;

    @Override
    public Goods buildGoods(GoodsSearchDTO goodsSearchDTO) {
        return null;
    }

    @Override
    public Shop buildShop(GoodsSearchDTO goodsSearchDTO) {
        return null;
    }

    @Override
    public PageVO<Goods> searchGoods(String keyword, QueryForm queryForm, PageForm pageForm) {
        return null;
    }

    @Override
    public PageVO<Shop> searchShop(String keyword, QueryForm queryForm, PageForm pageForm) {
        return null;
    }

    @Override
    public void createDoc(GoodsSearchDTO goodsInfo) {
    }

    @Override
    public void deleteDoc(String id) {
    }

    @Override
    public boolean updateGoodsSuggest(Goods goods) {
        return false;
    }

    @Override
    public boolean updateShopSuggest(Shop shop) {
        return false;
    }

    @Override
    public List<String> suggest(String prefix,String index) {
        if (StringUtils.isBlank(prefix)){
            return new ArrayList<>();
        }
        CompletionSuggestionBuilder suggestionBuilder = SuggestBuilders.completionSuggestion(
                "suggests"
        ).prefix(prefix).size(3);

        SuggestBuilder suggestBuilder = new SuggestBuilder();
        suggestBuilder.addSuggestion("autocompletion",suggestionBuilder);
        SearchRequestBuilder searchRequestBuilder = transportClient.prepareSearch(index)
                .setTypes("_doc")
                .suggest(suggestBuilder);
        SearchResponse searchResponse = searchRequestBuilder.get();
        Suggest suggest = searchResponse.getSuggest();
        Suggest.Suggestion autocompletion = suggest.getSuggestion("autocompletion");
        // 去掉重复的分词结果
        int maxSuggest = 0;
        Set<String> suggestSet = new HashSet<>();
        for (Object term : autocompletion.getEntries()) {
            if (term instanceof CompletionSuggestion.Entry){
                CompletionSuggestion.Entry item = (CompletionSuggestion.Entry) term;
                if (item.getOptions().isEmpty()){
                    continue;
                }
                for (CompletionSuggestion.Entry.Option option : item.getOptions()) {
                    String tip = option.getText().string();
                    if (suggestSet.contains(tip)){
                        continue;
                    }
                    suggestSet.add(tip);
                    maxSuggest++;
                }
            }
            if (maxSuggest > 3){
                break;
            }
        }
        return Stream.of(suggestSet.toArray(new String[]{})).collect(Collectors.toList());
    }

    /**
     * 查询条件
     * @param searchRequestForm
     * @param queryBuilder
     */
    protected void sort(String keyword, QueryForm searchRequestForm, NativeSearchQueryBuilder queryBuilder, PageForm pageForm) {
        queryBuilder.withQuery(
                QueryBuilders.matchQuery("keyword",keyword).operator(Operator.AND));

        if (StringUtils.isNotBlank(searchRequestForm.getOrderBy())){
            if (searchRequestForm.getOrderBy().equals("price") && searchRequestForm.getOrder().equals("asc")){
                queryBuilder.withSort(SortBuilders.fieldSort("price").order(SortOrder.ASC));
            }
            if (searchRequestForm.getOrderBy().equals("price") && searchRequestForm.getOrder().equals("desc")){
                queryBuilder.withSort(SortBuilders.fieldSort("price").order(SortOrder.DESC));
            }
            if (searchRequestForm.getOrderBy().equals("monthlySales")){
                queryBuilder.withSort(SortBuilders.fieldSort("monthlySales").order(SortOrder.DESC));
            }
            if (searchRequestForm.getOrderBy().equals("favorableRate")){
                queryBuilder.withSort(SortBuilders.fieldSort("favorableRate").order(SortOrder.DESC));
            }
        }
        queryBuilder.withPageable(
                PageRequest.of(pageForm.getPage() - 1, pageForm.getSize()));
    }

    /**
     * 封装分页结果
     * @param page
     * @param <T>
     * @return
     */
    protected <T> PageVO<T>  getPageVO(Page<T> page){
        PageVO<T> tPageVO = new PageVO<>();
        tPageVO.setContent(page.getContent())
                .setSize(TypeConversion.intToLong(page.getContent().size()))
                .setTotal(TypeConversion.intToLong(page.getTotalPages()));
        return tPageVO;
    }

    /**
     * 检查搜索关键字
     * @param keyword
     * @return
     */
    protected String checkKeyword(String keyword){
        if (StringUtils.isBlank(keyword)){
            return null;
        }
        return keyword.replace(" ","");
    }

}
