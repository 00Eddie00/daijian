package shop.daijian.support.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 *
 * 阿里云OSS配置文件
 *
 * @author liuxin
 * @date 2019/8/13 9:46
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "aliyun.oss")
public class AliyunOssProperties {

    /**
     * 阿里云OSS的endpoint
     */
    private String endpoint;

    /**
     * 阿里云OSS的bucket
     */
    private String bucket;

    /**
     * 阿里云OSS的rootUrl
     */
    private String rootUrl;

    /**
     * 阿里云OSS的dir
     */
    private String baseDir;

}



