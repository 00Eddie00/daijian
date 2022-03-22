package shop.daijian.trade.vo;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author liuxin
 * @since 2019/8/24 21:27
 */
@Data
@ApiModel(description = "商品评论视图信息")
@Accessors(chain = true)
public class GoodsCommentVO {

    @ApiModelProperty("评论ID")
    private String commentId;

    @ApiModelProperty("用户ID")
    private String userId;

    @ApiModelProperty("用户昵称")
    private String nickName;

    @ApiModelProperty("用户头像链接")
    private String avatarUrl;

    @ApiModelProperty("星级")
    private Integer star;

    @ApiModelProperty("正文")
    private String content;

    @ApiModelProperty("配图链接列表")
    private List<String> imageUrlList;

    @ApiModelProperty("创建时间")
    private LocalDateTime createTime;

}



