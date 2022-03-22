package shop.daijian.platform.repository;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import shop.daijian.platform.entity.Shop;

/**
 * @Author stronghwan
 * @Verison
 * @Date2019/8/13-11-19
 */
public interface ShopRepository extends ElasticsearchRepository<Shop,String> {
}
