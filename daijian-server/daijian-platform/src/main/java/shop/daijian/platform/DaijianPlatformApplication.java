package shop.daijian.platform;

import com.spring4all.swagger.EnableSwagger2Doc;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import shop.daijian.platform.repository.mybatis.CmsContentMapper;

@EnableSwagger2Doc
@EnableDiscoveryClient
@SpringBootApplication
@MapperScan(basePackages = "shop.daijian.platform.repository")
public class DaijianPlatformApplication {

    public static void main(String[] args) {
        SpringApplication.run(DaijianPlatformApplication.class, args);
    }

}
