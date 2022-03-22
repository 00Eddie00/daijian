package shop.daijian.user.form;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;
import shop.daijian.common.constraints.URL;
import shop.daijian.user.enums.GenderEnum;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import java.time.LocalDate;

/**
 * @author suyutong
 * @since 2019/8/8 16:55
 */
@ApiModel(description = "用户信息表单")
@Data
public class UserInfoForm {

    /**
     * 昵称
     */
    @NotBlank(message = "昵称不能为空")
    @Length(min = 3, max = 20, message = "昵称应在3到20位之间")
    @ApiModelProperty(value = "昵称")
    private String nickName;

    /**
     * 头像链接
     */
    @URL
    @NotBlank
    @ApiModelProperty(value = "头像链接")
    private String avatarUrl;

    /**
     * 性别
     */
    @NotNull
    @ApiModelProperty(value = "性别")
    private GenderEnum gender;

    /**
     * 生日
     */
    @Past
    @ApiModelProperty(value = "生日")
    private LocalDate birthday;

}
