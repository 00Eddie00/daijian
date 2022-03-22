package shop.daijian.user.dto;



import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.HashMap;
/**
 * {<goodsId,goodsNum>, <goodsId,goodsNum>, ......}
 * 从学长项目学来的存储方式，节约缓存空间，加快查询速度，但在包装并返回给前端的过程中会消耗更多资源
 *
 * @author hanshizhou
 * @since 2019/8/11
 **/
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class CartDTO extends HashMap<String, Integer> {

    /**
     * 继承父类的toString方法
     * @return
     */
    @Override
    public String toString() {
        return super.toString();
    }

}
