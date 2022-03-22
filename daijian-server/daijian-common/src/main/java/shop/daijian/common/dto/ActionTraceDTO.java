package shop.daijian.common.dto;

import lombok.Data;
import lombok.experimental.Accessors;
import shop.daijian.common.enums.ActionTypeEnum;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 用户行为（点赞、收藏、关注）
 * </p>
 *
 * @author qiyubing
 * @since 2019-08-14
 */
@Data
@Accessors(chain = true)
public class ActionTraceDTO implements Serializable {

    private static final long serialVersionUID = 7152624685862213612L;

    /**
     * 行为ID
     */
    private String actionTraceId;

    /**
     * 用户ID
     */
    private String source;

    /**
     * 目标
     */
    private String target;

    /**
     * 动作类型
     */
    private ActionTypeEnum type;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;
}
