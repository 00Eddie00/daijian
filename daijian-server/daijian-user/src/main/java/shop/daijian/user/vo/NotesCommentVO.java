package shop.daijian.user.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * @author qiyubing
 * @since 2019-08-28
 */
@ApiModel(description = "手记评论")
@Data
@Accessors(chain = true)
public class NotesCommentVO {

    @ApiModelProperty("评论ID")
    private String commentId;

    /**
     * 用户ID
     */
    private String userId;

    /**
     * 昵称
     */
    private String nickName;

    /**
     * 头像链接
     */
    private String avatarUrl;

    /**
     * 正文
     */
    private String content;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

}
