package shop.daijian.support.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import shop.daijian.common.enums.CommentTypeEnum;
import shop.daijian.common.support.BaseEntity;

/**
 * <p>
 * VIEW
 * </p>
 *
 * @author ASUS
 * @since 2019-08-26
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("comment_view")
public class CommentView extends BaseEntity {

    /**
     * 评论ID
     */
    @TableField("comment_id")
    private String commentId;

    /**
     * 目标ID
     */
    @TableField("target_id")
    private String targetId;

    /**
     * 用户ID
     */
    @TableField("user_id")
    private String userId;

    /**
     * 头像链接
     */
    @TableField("avatar_url")
    private String avatarUrl;

    /**
     * 昵称
     */
    @TableField("nick_name")
    private String nickName;

    /**
     * 星级
     */
    @TableField("star")
    private Integer star;

    /**
     * 正文
     */
    @TableField("content")
    private String content;

    /**
     * 配图jsonarray
     */
    @TableField("image_url_json")
    private String imageUrlJson;

    /**
     * 评论类型
     */
    @TableField("target_type")
    private CommentTypeEnum commentTypeEnum;

    /**
     * 链接
     */
    @TableField("unique_id")
    private String uniqueId;

}
