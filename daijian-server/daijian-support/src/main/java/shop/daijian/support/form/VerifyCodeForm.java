package shop.daijian.support.form;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import shop.daijian.common.constraints.Mobile;
import shop.daijian.common.enums.SmsTypeEnum;

import javax.validation.constraints.NotNull;

/**
 * @author qiyubing
 * @since 2019-07-26
 */
@ApiModel(description = "发送验证码表单")
@Data
public class VerifyCodeForm {

    @ApiModelProperty("手机号")
    @Mobile
    private String mobile;

    @ApiModelProperty("短信类型")
    @NotNull
    private SmsTypeEnum smsTypeEnum;

}
