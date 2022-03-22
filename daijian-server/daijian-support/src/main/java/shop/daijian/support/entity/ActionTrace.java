package shop.daijian.support.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import shop.daijian.common.enums.ActionTypeEnum;
import shop.daijian.common.support.BaseEntity;

/**
 * <p>
 * 用户行为（点赞、收藏、关注）
 * </p>
 *
 * @author qiyubing
 * @since 2019-08-14
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("action_trace")
public class ActionTrace extends BaseEntity {

    /**
     * 行为ID
     */
    @TableId(value = "action_trace_id", type = IdType.ID_WORKER_STR)
    private String actionTraceId;

    /**
     * 用户ID
     */
    @TableField("source")
    private String source;

    /**
     * 目标
     */
    @TableField("target")
    private String target;

    /**
     * 动作类型
     */
    @TableField("type")
    private ActionTypeEnum type;

}
