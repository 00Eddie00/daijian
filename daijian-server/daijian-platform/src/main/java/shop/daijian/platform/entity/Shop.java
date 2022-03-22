package shop.daijian.platform.entity;

import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.CompletionField;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.util.List;

/**
 * @Author stronghwan
 * @Verison
 * @Date2019/8/13-10-54
 */
@Data
@Accessors(chain = true)
@Document(indexName = "shop", type = "_doc", shards = 1, replicas = 0)
public class Shop {

    @Id
    private String shopId;

    @Field(type = FieldType.Text, analyzer = "ik_smart")
    private String keyword;

    @Field(type = FieldType.Keyword)
    private String name;

    @Field(type = FieldType.Keyword, index = false)
    private String avatarUrl;

    @Field(type = FieldType.Integer)
    private Integer monthlySales;

    @Field(type = FieldType.Keyword)
    private String originRegion;

    @Field(type = FieldType.Integer)
    private Integer credit;

    @CompletionField
    private List<ShopSuggest> suggests;
}
