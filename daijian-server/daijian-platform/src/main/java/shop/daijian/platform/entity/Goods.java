package shop.daijian.platform.entity;

import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.CompletionField;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 搜索类
 * sku可封装json
 *
 * @Author stronghwan
 * @Verison
 * @Date2019/8/5-09-38
 */
@Data
@Accessors(chain = true)
@Document(indexName = "goods", type = "_doc", shards = 1, replicas = 0)
public class Goods {
    @Id
    private String goodsId;

    @Field(type = FieldType.Text, analyzer = "ik_smart")
    private String keyword;

    @Field(type = FieldType.Text, analyzer = "ik_smart")
    private String category;

    @Field(type = FieldType.Text, analyzer = "ik_smart")
    private String name;

    @Field(type = FieldType.Keyword, index = false)
    private String categoryId;

    @Field(type = FieldType.Keyword, index = false)
    private String avatarUrl;

    @Field(type = FieldType.Keyword)
    private String originRegion;

    @Field(type = FieldType.Keyword, index = false)
    private BigDecimal unitPrice;

    @Field(type = FieldType.Keyword, index = false)
    private BigDecimal originPrice;

    @Field(type = FieldType.Text, analyzer = "ik_smart")
    private String specification;

    @Field(type = FieldType.Keyword, index = false)
    private int monthlySales;

    @Field(type = FieldType.Text, analyzer = "ik_smart")
    private String shopName;

    @Field(type = FieldType.Integer)
    private int favorableRate;

    @Field(type = FieldType.Keyword, index = false)
    private Date createTime;

    @CompletionField
    private List<GoodsSuggest> suggests;
}
