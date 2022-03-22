package shop.daijian.user.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import shop.daijian.common.support.BaseEntity;

/**
 * <p>
 * 手记信息
 * </p>
 *
 * @author qiyubing
 * @since 2019-08-12
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("note_info")
public class NotesInfo extends BaseEntity {

    /**
     * 手记ID
     */
    @TableId(value = "note_id", type = IdType.ID_WORKER_STR)
    private String notesId;

    /**
     * 庄园主用户ID
     */
    @TableField("user_id")
    private String userId;

    /**
     * 庄园头像链接
     */
    @TableField("manor_avatar_url")
    private String manorAvatarUrl;

    /**
     * 庄园名
     */
    @TableField("manor_name")
    private String manorName;

    /**
     * 商品ID
     */
    @TableField("goods_id")
    private String goodsId;

    /**
     * 封面图
     */
    @TableField("cover_image_url")
    private String coverImageUrl;

    /**
     * 标题
     */
    @TableField("title")
    private String title;

    /**
     * 手记正文HTML代码
     */
    @TableField("content")
    private String content;

    /**
     * 点赞数
     */
    @TableField("like_num")
    private Integer likeNum;

    /**
     * 浏览量
     */
    @TableField("view_num")
    private Integer viewNum;

    /**
     * 评论数
     */
    @TableField("comment_num")
    private Integer commentNum;

}
