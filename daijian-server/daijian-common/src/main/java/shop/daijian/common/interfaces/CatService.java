package shop.daijian.common.interfaces;

/**
 * @author liuxin
 * @date 2019/8/8 8:45
 */
public interface CatService {
    /**
     * 根据catBackId获取商品分类链
     *
     * @param catBackId
     * @return 商品分类链
     */
    String getCatKeyword(String catBackId);
}
