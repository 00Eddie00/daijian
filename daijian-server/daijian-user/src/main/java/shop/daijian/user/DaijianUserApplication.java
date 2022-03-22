package shop.daijian.user;

import com.spring4all.swagger.EnableSwagger2Doc;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;
import shop.daijian.user.repository.mybatis.*;
//@MapperScan(basePackageClasses = {AuthInfoMapper.class, ManorInfoMapper.class, NotesInfoMapper.class, UserInfoMapper.class,UserAddressMapper.class,UserActionMapper.class})
@MapperScan(basePackages = "shop.daijian.user.repository.mybatis")
@EnableSwagger2Doc
@EnableDiscoveryClient
@SpringBootApplication
public class DaijianUserApplication {

    public static void main(String[] args) {
        SpringApplication.run(DaijianUserApplication.class, args);
    }

}


