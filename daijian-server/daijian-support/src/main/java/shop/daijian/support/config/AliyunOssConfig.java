package shop.daijian.support.config;

import com.aliyun.oss.OSSClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import shop.daijian.common.config.AliyunProperties;

/**
 * @author qiyubing
 * @since 2019-08-26
 */
@Configuration
public class AliyunOssConfig {

    @Autowired
    private AliyunProperties aliyunProperties;

    @Autowired
    private AliyunOssProperties aliyunOssProperties;

    @Bean
    public OSSClient ossClient() {
        return new OSSClient(aliyunOssProperties.getEndpoint(), aliyunProperties.getAccessKeyId(), aliyunProperties.getAccessKeySecret());
    }
}
