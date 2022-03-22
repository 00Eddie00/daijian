package shop.daijian.support.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * 阿里云短信参数
 *
 * @author qiyubing
 * @since 2019-07-26
 */
@Data
@EnableConfigurationProperties
@Configuration
@ConfigurationProperties(prefix = "aliyun.sms")
public class AliyunSmsProperties {

    /**
     * 产品名称:云通信短信API产品
     */
    private String product = "Dysmsapi";

    /**
     * 产品域名
     */
    private String domain = "dysmsapi.aliyuncs.com";
}
