package shop.daijian.common.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * 阿里云配置文件
 *
 * @author qiyubing
 * @since 2019-07-26
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "aliyun")
public class AliyunProperties {

    /**
     * 授权账户的ACCESS_KEY_ID
     */
    private String accessKeyId;

    /**
     * 授权账户的ACCESS_KEY_SECRET
     */
    private String accessKeySecret;
}
