package shop.daijian.common.dto;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.experimental.Accessors;
import shop.daijian.common.enums.CommentTypeEnum;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @author liuxin
 * @since 2019/8/26 15:41
 */
@Data
@ApiModel(description = "评论信息")
@Accessors(chain = true)
public class CommentDTO implements Serializable {

    private static final long serialVersionUID = 9166834461521319026L;

    /**
     * 评论Id
     */
    private String commentId;

    /**
     * 目标ID，如商品ID、手记ID
     */
    private String targetId;

    /**
     * 用户ID
     */
    private String userId;

    /**
     * 判断重复的ID,如订单ID
     */
    private String uniqueId;

    /**
     * 昵称
     */
    private String nickName;

    /**
     * 头像链接
     */
    private String avatarUrl;

    /**
     * 星级
     */
    private Integer star;

    /**
     * 正文
     */
    private String content;

    /**
     * 配图链接列表
     */
    private List<String> imageUrlList;

    /**
     * 评论类型
     */
    private CommentTypeEnum commentTypeEnum;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

}



