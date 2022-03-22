package  com.yuntuan.auth.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import  com.yuntuan.server.support.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 用户认证
 * </p>
 *
 * @author DELL
 * @since 2020-03-02
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("auth_info")
public class AuthInfo extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 认证ID
     */
    @TableId(value = "auth_id", type = IdType.ID_WORKER_STR)
    private String authId;

    /**
     * 用户ID
     */
    @TableField("user_id")
    private String userId;

    /**
     * 认证类型
     */
    @TableField("identity_type")
    private String identityType;

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
