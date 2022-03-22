package shop.daijian.user.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * JWT实体
 *
 * @author qiyubing
 * @since 2019-07-20
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Jwt implements Serializable {

    private static final long serialVersionUID = 853110217597667102L;

    /**
     * 令牌
     */
    private String token;

    /**
     * 有效截止日期
     */
    private Long expiresAt;

}
