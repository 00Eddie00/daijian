package shop.daijian.seller;

import com.spring4all.swagger.EnableSwagger2Doc;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import shop.daijian.seller.repository.mybatis.GoodsInfoMapper;

/**
 * @author liuxin
 * @date 2019/8/8 8:45
 */
@MapperScan(basePackageClasses = GoodsInfoMapper.class)
@EnableSwagger2Doc
@EnableDiscoveryClient
@SpringBootApplication
public class DaijianGoodsApplication {

    public static void main(String[] args) {
        SpringApplication.run(DaijianGoodsApplication.class, args);
    }

}
