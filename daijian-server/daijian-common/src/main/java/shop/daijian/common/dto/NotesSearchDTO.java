package shop.daijian.common.dto;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 手记搜索信息
 * 排序规则：浏览量(热门)、创建时间(最新)
 * 索引：标题、庄园名、商品名、商品分类链
 *
 * @author qiyubing
 * @since 2019-08-13
 */
@Data
@Accessors(chain = true)
public class NotesSearchDTO implements Serializable {

    private static final long serialVersionUID = 1567745288475007102L;

    /**
     * 手记ID
     */
    private String notesId;

    /**
     * 用户ID
     */
    private String userId;

    /**
     * 庄园头像链接
     */
    private String manorAvatarUrl;

    /**
     * 庄园名
     */
    private String manorName;

    /**
     * 商品ID
     */
    private String goodsId;

    /**
     * 封面图
     */
    private String coverImageUrl;

    /**
     * 标题
     */
    private String title;

    /**
     * 点赞数
     */
    private Integer likeNum;

    /**
     * 浏览量
     */
    private Integer viewNum;

    /**
     * 评论数
     */
    private Integer commentNum;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

}
