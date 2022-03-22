package shop.daijian.common.dto;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * 用户简略信息
 *
 * @author suyutong
 * @since 2019/8/4 16:55
 */
@Data
@Accessors(chain = true)
public class UserBriefDTO implements Serializable {

    private static final long serialVersionUID = 8332612127030109917L;

    /**
     * 用户ID
     */
    private String userId;

    /**
     * 昵称
     */
    private String nickName;

    /**
     * 头像链接
     */
    private String avatarUrl;

}
