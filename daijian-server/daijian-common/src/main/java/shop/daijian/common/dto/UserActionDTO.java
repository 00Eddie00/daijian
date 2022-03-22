package shop.daijian.common.dto;

import lombok.Data;
import lombok.experimental.Accessors;
import shop.daijian.common.enums.ActionTypeEnum;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 用户行为
 * </p>
 *
 * @author qiyubing
 * @since 2019-08-11
 */
@Data
@Accessors(chain = true)
public class UserActionDTO implements Serializable {

    private static final long serialVersionUID = -6643126781298450757L;

    /**
     * 用户行为ID
     */
    private String userActionId;

    /**
     * 用户ID
     */
    private String userId;

    /**
     * 目标
     */
    private String target;

    /**
     * 动作
     */
    private ActionTypeEnum action;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;

}
