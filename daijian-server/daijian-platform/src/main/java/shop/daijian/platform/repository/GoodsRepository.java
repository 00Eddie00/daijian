package shop.daijian.platform.repository;


import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import shop.daijian.platform.entity.Goods;

/**
 * @Author stronghwan
 * @Verison
 * @Date2019/8/5-10-26
 */
public interface GoodsRepository extends ElasticsearchRepository<Goods, String> {
}
