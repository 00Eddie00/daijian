package shop.daijian.common.config;

import com.baomidou.mybatisplus.autoconfigure.MybatisPlusAutoConfiguration;
import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.baomidou.mybatisplus.core.injector.ISqlInjector;
import com.baomidou.mybatisplus.extension.injector.LogicSqlInjector;
import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.ZoneId;
import java.time.ZonedDateTime;

/**
 * MybatisPlus配置
 *
 * @author qiyubing
 * @since 2019-07-24
 */
@ConditionalOnBean(MybatisPlusAutoConfiguration.class)
@Configuration
public class MyBatisPlusConfig {

    /**
     * 逻辑删除注入器
     */
    @Bean
    public ISqlInjector sqlInjector() {
        return new LogicSqlInjector();
    }

    /**
     * 自动填充
     */
    @Bean
    public MetaObjectHandler metaObjectHandler() {
        return new MetaObjectHandler() {
            @Override
            public void insertFill(MetaObject metaObject) {
                this.setFieldValByName("createTime", ZonedDateTime.now(ZoneId.of("Asia/Shanghai")).toLocalDateTime(), metaObject);
                this.setFieldValByName("updateTime", ZonedDateTime.now(ZoneId.of("Asia/Shanghai")).toLocalDateTime(), metaObject);
            }

            @Override
            public void updateFill(MetaObject metaObject) {
                this.setFieldValByName("updateTime", ZonedDateTime.now(ZoneId.of("Asia/Shanghai")).toLocalDateTime(), metaObject);
            }
        };
    }

    /**
     * 分页插件
     */
    @Bean
    public PaginationInterceptor paginationInterceptor() {
        return new PaginationInterceptor();
    }
}