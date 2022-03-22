package shop.daijian.user.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import shop.daijian.common.enums.UserRoleEnum;
import shop.daijian.user.enums.GenderEnum;

import java.time.LocalDate;

/**
 * 用户信息详情
 *
 * @author suyutong
 * @since 2019/8/4 17:52
 */
@Data
public class UserDetailVO {

    @ApiModelProperty("用户ID")
    private String userId;

    @ApiModelProperty("昵称")
    private String nickName;

    @ApiModelProperty("头像链接")
    private String avatarUrl;

    @ApiModelProperty("性别")
    private GenderEnum gender;

    @ApiModelProperty("生日")
    private LocalDate birthday;

    @ApiModelProperty("信用值")
    private Integer credit;

    @ApiModelProperty("简介")
    private String introduction;

    @ApiModelProperty("庄园关注数")
    private Integer followNum;

    @ApiModelProperty("商品收藏数")
    private Integer favoriteNum;

    @ApiModelProperty("用户角色")
    private UserRoleEnum role;

}
