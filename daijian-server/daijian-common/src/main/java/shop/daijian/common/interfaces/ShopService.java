package shop.daijian.common.interfaces;

import shop.daijian.common.dto.ShopBriefDTO;
import shop.daijian.common.dto.ShopSearchDTO;
import shop.daijian.common.enums.ShopCreditEnum;
import shop.daijian.common.form.PageForm;
import shop.daijian.common.vo.PageVO;

import java.util.List;

/**
 * @author hanshizhou
 * @since 2019-08-08
 */
public interface ShopService {

    /**
     * 查询店铺简略信息
     *
     * @param shopId 商品ID
     * @return ShopBriefDTO 简略信息对象
     */
    ShopBriefDTO getShopBrief(String shopId);

    /**
     * 根据店铺ID列表获得店铺简要信息列表（保证顺序，但不保证缺失）
     *
     * @param shopIdList      店铺ID列表
     * @param previewGoodsNum 预览商品个数
     * @return 店铺简要信息列表
     */
    List<ShopBriefDTO> listByIdList(List<String> shopIdList, Integer previewGoodsNum);

    /**
     * 修改店铺信誉值
     *
     * @param shopId         店铺ID
     * @param shopCreditEnum 店铺加减分项枚举
     */
    void setCredit(String shopId, ShopCreditEnum shopCreditEnum);

    /**
     * 获取店铺搜索信息
     */
    ShopSearchDTO getShopSearchInfo(String shopId);

    /**
     * 分页获取店铺搜索信息
     */
    PageVO<ShopSearchDTO> pageShopSearchInfo(PageForm pageForm);
}
