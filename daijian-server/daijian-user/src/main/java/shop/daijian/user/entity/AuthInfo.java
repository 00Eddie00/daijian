package shop.daijian.user.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import shop.daijian.common.enums.IdentityTypeEnum;
import shop.daijian.common.support.BaseEntity;


/**
 * <p>
 * 认证信息实体
 * </p>
 *
 * @author qiyubing
 * @since 2019-07-31
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("auth_info")
public class AuthInfo extends BaseEntity {

    /**
     * 认证信息ID
     */
    @TableId(value = "auth_id", type = IdType.ID_WORKER_STR)
    private String authId;

    /**
     * 用户ID
     */
    @TableField("user_id")
    private String userId;

    /**
     * 登录类型
     */
    @TableField("identity_type")
    private IdentityTypeEnum identityTypeEnum;

    /**
     * 标识
     */
    @TableField("identifier")
    private String identifier;

    /**
     * 凭证
     */
    @TableField("credential")
    private String credential;

}
