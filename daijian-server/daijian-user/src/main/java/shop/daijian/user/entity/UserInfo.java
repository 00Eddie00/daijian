package shop.daijian.user.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import shop.daijian.common.enums.UserRoleEnum;
import shop.daijian.common.support.BaseEntity;
import shop.daijian.user.enums.GenderEnum;

import java.time.LocalDate;

/**
 * <p>
 * 用户信息
 * </p>
 *
 * @author suyutong
 * @since 2019-08-04
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("user_info")
public class UserInfo extends BaseEntity {

    /**
     * 用户ID
     */
    @TableId(value = "user_id", type = IdType.ID_WORKER_STR)
    private String userId;

    /**
     * 昵称
     */
    @TableField("nick_name")
    private String nickName;

    /**
     * 头像链接
     */
    @TableField("avatar_url")
    private String avatarUrl;

    /**
     * 性别
     */
    @TableField("gender")
    private GenderEnum gender;

    /**
     * 生日
     */
    @TableField("birthday")
    private LocalDate birthday;

    /**
     * 信用值
     */
    @TableField("credit")
    private Integer credit;

    /**
     * 简介
     */
    @TableField("introduction")
    private String introduction;

    /**
     * 用户角色
     */
    @TableField("role")
    private UserRoleEnum role;

}
