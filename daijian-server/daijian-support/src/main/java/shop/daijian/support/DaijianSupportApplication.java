package shop.daijian.support;

import com.spring4all.swagger.EnableSwagger2Doc;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import shop.daijian.support.mybatis.RegionInfoMapper;

/**
 * @author liuxin
 * @date 2019/8/8 8:45
 */
@MapperScan(basePackageClasses = RegionInfoMapper.class)
@EnableSwagger2Doc
@EnableDiscoveryClient
@SpringBootApplication
public class DaijianSupportApplication {

    public static void main(String[] args) {
        SpringApplication.run(DaijianSupportApplication.class, args);
    }

}
