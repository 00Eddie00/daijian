package shop.daijian.platform.entity;

import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.CompletionField;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.util.Date;
import java.util.List;

/**
 * @Author stronghwan
 * @Verison
 * @Date2019/8/13-17-04
 */
@Data
@Accessors(chain = true)
@Document(indexName = "note", type = "_doc", shards = 1, replicas = 0)
//@Document(indexName = "note", type = "_doc", shards = 1, replicas = 0, createIndex = false)
public class Note {

    @Field(type = FieldType.Text, analyzer = "ik_smart")
    private String keyword;
    /**
     * 用户手记ID
     */
    @Id
    private String notesId;

    /**
     * 用户ID
     */
    @Field(type = FieldType.Keyword, index = false)
    private String userId;

    /**
     * 庄园头像链接
     */
    @Field(type = FieldType.Keyword, index = false)
    private String manorAvatarUrl;

    /**
     * 庄园名
     */
    @Field(type = FieldType.Text, analyzer = "ik_smart")
    private String manorName;

    /**
     * 商品ID
     */
    @Field(type = FieldType.Keyword, index = false)
    private String goodsId;

    /**
     * 封面图
     */
    @Field(type = FieldType.Keyword, index = false)
    private String coverImageUrl;

    /**
     * 标题
     */
    @Field(type = FieldType.Text, analyzer = "ik_smart")
    private String title;

    /**
     * 点赞数
     */
    @Field(type = FieldType.Keyword, index = false)
    private Integer likeNum;

    /**
     * 浏览量
     */
    @Field(type = FieldType.Keyword, index = false)
    private Integer viewNum;

    /**
     * 评论数
     */
    @Field(type = FieldType.Keyword, index = false)
    private Integer commentNum;

    /**
     * 创建时间
     */
    @Field(type = FieldType.Keyword, index = false)
    private Date createTime;
    @CompletionField
    private List<NoteSuggest> suggests;
}
