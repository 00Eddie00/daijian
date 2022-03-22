package shop.daijian.user.vo;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import shop.daijian.common.dto.UserBriefDTO;
import shop.daijian.user.entity.Jwt;

/**
 * @author qiyubing
 * @since 2019-08-26
 */
@ApiModel("登录信息")
@Data
public class LoginVO {

    private Jwt jwt;

    private UserBriefDTO userBriefDTO;

}
