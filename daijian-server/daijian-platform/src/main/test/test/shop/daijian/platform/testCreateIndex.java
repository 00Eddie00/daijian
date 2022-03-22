package shop.daijian.platform;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.test.context.junit4.SpringRunner;
import shop.daijian.common.dto.GoodsSearchDTO;
import shop.daijian.platform.entity.Goods;
import shop.daijian.platform.entity.Note;
import shop.daijian.platform.entity.Shop;
import shop.daijian.platform.service.GoodsSearchService;

import java.math.BigDecimal;

@RunWith(SpringRunner.class)
@SpringBootTest
public class testCreateIndex {

    @Autowired
    private ElasticsearchTemplate elasticsearchTemplate;

    @Autowired
    private GoodsSearchService goodsSearchService;

    @Test
    public void testCreateIndex() {
        elasticsearchTemplate.createIndex(Goods.class);
        elasticsearchTemplate.putMapping(Goods.class);
        elasticsearchTemplate.createIndex(Note.class);
        elasticsearchTemplate.putMapping(Note.class);
        elasticsearchTemplate.createIndex(Shop.class);
        elasticsearchTemplate.putMapping(Shop.class);
    }

    @Test
    public void testAddObj(){
        GoodsSearchDTO goodsSearchDTO = new GoodsSearchDTO();
        goodsSearchDTO.setGoodsId("1");
        goodsSearchDTO.setAvatarUrl("www.pornhub.com");
        goodsSearchDTO.setCatBackId("1");
        goodsSearchDTO.setFavorableRate(10);
        goodsSearchDTO.setMonthlySales(200);
        goodsSearchDTO.setName("asd");
        goodsSearchDTO.setOriginPrice(new BigDecimal(2));
        goodsSearchDTO.setOriginRegion("tyc");
        goodsSearchDTO.setShopName("kfc");
        goodsSearchDTO.setUnitPrice(new BigDecimal(1));
        goodsSearchDTO.setSpecification("1*5*3");
        goodsSearchService.createDoc(goodsSearchDTO);
    }
}
