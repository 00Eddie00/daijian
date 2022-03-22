package shop.daijian.support.config;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import shop.daijian.common.config.AliyunProperties;

/**
 * 阿里云短信自动配置
 *
 * @author qiyubing
 * @since 2019-07-26
 */
@Configuration
@AllArgsConstructor
public class AliyunSmsAutoConfiguration {

    private AliyunProperties aliyunProperties;

    private AliyunSmsProperties aliyunSmsProperties;

    @Bean
    public IAcsClient acsClient() throws ClientException {
        //可自助调整超时时间
        System.setProperty("sun.net.client.defaultConnectTimeout", "10000");
        System.setProperty("sun.net.client.defaultReadTimeout", "10000");

        //初始化acsClient,暂不支持region化
        IClientProfile profile = DefaultProfile.getProfile("cn-hangzhou", aliyunProperties.getAccessKeyId(), aliyunProperties.getAccessKeySecret());
        DefaultProfile.addEndpoint("cn-hangzhou", "cn-hangzhou", aliyunSmsProperties.getProduct(), aliyunSmsProperties.getDomain());
        return new DefaultAcsClient(profile);
    }
}
