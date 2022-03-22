package shop.daijian.trade;

import com.spring4all.swagger.EnableSwagger2Doc;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import shop.daijian.trade.repository.mybatis.OrderInfoMapper;

@MapperScan(basePackageClasses = OrderInfoMapper.class)
@EnableSwagger2Doc
@EnableDiscoveryClient
@SpringBootApplication
public class DaijianTradeApplication {

    public static void main(String[] args) {
        SpringApplication.run(DaijianTradeApplication.class, args);
    }

}
