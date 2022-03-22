package shop.daijian.user.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import shop.daijian.common.enums.ActionTypeEnum;
import shop.daijian.common.support.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 用户行为
 * </p>
 *
 * @author qiyubing
 * @since 2019-08-11
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("user_action")
public class UserAction extends BaseEntity {

    /**
     * 用户行为ID
     */
    @TableId(value = "user_action_id", type = IdType.ID_WORKER_STR)
    private String userActionId;

    /**
     * 用户ID
     */
    @TableField("user_id")
    private String userId;

    /**
     * 目标
     */
    @TableField("target")
    private String target;

    /**
     * 动作
     */
    @TableField("action")
    private ActionTypeEnum action;

}
