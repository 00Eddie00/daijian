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
import shop.daijian.common.form.PageForm;
import shop.daijian.common.form.QueryForm;
import shop.daijian.common.vo.PageVO;
import shop.daijian.platform.service.BaseSerachService;
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
 * @Date2019/8/13-16-36
 */
public class BaseSearchServiceImpl implements BaseSerachService {

    @Autowired
    private TransportClient transportClient;

    @Override
    public List<String> suggest(String prefix,String index, Integer size) {
        if (StringUtils.isBlank(prefix)){
            return new ArrayList<>();
        }
        CompletionSuggestionBuilder suggestionBuilder = SuggestBuilders.completionSuggestion(
                "suggests"
        ).prefix(prefix).size(size);

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
                QueryBuilders.matchQuery("keyword",keyword).operator(Operator.OR));

        if (StringUtils.isNotBlank(searchRequestForm.getOrderBy())){
            if (searchRequestForm.getOrderBy().equals("unit_price") && searchRequestForm.getOrder().equals("asc")){
                queryBuilder.withSort(SortBuilders.fieldSort("unitPrice").order(SortOrder.ASC));
            }
            if (searchRequestForm.getOrderBy().equals("unit_price") && searchRequestForm.getOrder().equals("desc")){
                queryBuilder.withSort(SortBuilders.fieldSort("unitPrice").order(SortOrder.DESC));
            }
            if (searchRequestForm.getOrderBy().equals("monthly_sales")){
                queryBuilder.withSort(SortBuilders.fieldSort("monthlySales").order(SortOrder.DESC));
            }
            if (searchRequestForm.getOrderBy().equals("favorable_rate")){
                queryBuilder.withSort(SortBuilders.fieldSort("favorableRate").order(SortOrder.DESC));
            }
//            if(searchRequestForm.getOrderBy().equals("viewNum")){
//                queryBuilder.withSort(SortBuilders.fieldSort("viewNum").order(SortOrder.DESC));
//            }
            if(searchRequestForm.getOrderBy().equals("view_num")){
                queryBuilder.withSort(SortBuilders.fieldSort("viewNum").order(SortOrder.DESC));
            }
            if (searchRequestForm.getOrderBy().equals("create_time")){
                queryBuilder.withSort(SortBuilders.fieldSort("createTime").order(SortOrder.DESC));
            }
            // todo
//            if (searchRequestForm.getOrderBy().equals("multipleGoods")){
//                queryBuilder.withSort(SortBuilders.fieldSort("monthlySales").order(SortOrder.DESC));
//                queryBuilder.withSort(SortBuilders.fieldSort("favorableRate").order(SortOrder.DESC));
//            }
//            if (searchRequestForm.getOrderBy().equals("multipleShop")){
//                queryBuilder.withSort(SortBuilders.fieldSort("monthlySales").order(SortOrder.DESC));
//                queryBuilder.withSort(SortBuilders.fieldSort("credit").order(SortOrder.DESC));
//            }
//            if (searchRequestForm.getOrderBy().equals("multipleNote")){
//                queryBuilder.withSort(SortBuilders.fieldSort("createTime").order(SortOrder.DESC));
//                queryBuilder.withSort(SortBuilders.fieldSort("viewNum").order(SortOrder.DESC));
//            }
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
    protected <T> PageVO<T> getPageVO(Page<T> page){
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
