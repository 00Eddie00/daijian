package shop.daijian.support.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import shop.daijian.common.enums.CommentTypeEnum;
import shop.daijian.common.support.BaseEntity;

/**
 * <p>
 * 商品评价
 * </p>
 *
 * @author ASUS
 * @since 2019-08-26
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("comment_info")
public class CommentInfo extends BaseEntity {

    /**
     * 评论ID
     */
    @TableId(value = "comment_id", type = IdType.ID_WORKER_STR)
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
     * 判断重复的ID,如订单ID，若没有也不能为空，与targetId一致即可
     */
    @TableField("unique_id")
    private String uniqueId;

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
     * 配图JSON数组
     */
    @TableField("image_url_json")
    private String imageUrlJson;

    /**
     * 评论类型
     */
    @TableField("target_type")
    private CommentTypeEnum commentTypeEnum;

}
