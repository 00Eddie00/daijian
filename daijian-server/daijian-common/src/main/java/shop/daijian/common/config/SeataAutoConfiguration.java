package shop.daijian.common.config;

import io.seata.rm.datasource.DataSourceProxy;
import io.seata.spring.annotation.GlobalTransactionScanner;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

/**
 * Seata自动配置
 *
 * @author qiyubing
 * @since 2019-07-20
 */
@Slf4j
@ConditionalOnClass(GlobalTransactionScanner.class)
@AutoConfigureBefore(name = "mybatisPlusAutoConfiguration")
@AutoConfigureAfter({DataSourceAutoConfiguration.class})
@Configuration
public class SeataAutoConfiguration implements ApplicationContextAware {

    @Value("${spring.application.name}")
    private String applicationName;

    @Bean
    public GlobalTransactionScanner globalTransactionScanner() {
        String groupName = "my_test_tx_group";
        if (applicationName == null) {
            return new GlobalTransactionScanner(groupName);
        } else {
            return new GlobalTransactionScanner(applicationName, groupName);
        }
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        DataSource originDataSource = applicationContext.getBean(DataSource.class);
        log.debug("originDataSource = {}", applicationContext.getBean("dataSource"));
        DefaultListableBeanFactory defaultListableBeanFactory = (DefaultListableBeanFactory) applicationContext.getAutowireCapableBeanFactory();
        // 从Spring容器中删除Bean
        defaultListableBeanFactory.removeBeanDefinition("dataSource");
        DataSourceProxy dataSourceProxy = new DataSourceProxy(originDataSource);
        // 向Spring容器中注册
        BeanDefinitionBuilder beanDefinitionBuilder = BeanDefinitionBuilder.genericBeanDefinition(DataSource.class, () -> dataSourceProxy);
        defaultListableBeanFactory.registerBeanDefinition("dataSource", beanDefinitionBuilder.getBeanDefinition());
        log.debug("proxyDataSource = {}", applicationContext.getBean("dataSource"));
    }
}
