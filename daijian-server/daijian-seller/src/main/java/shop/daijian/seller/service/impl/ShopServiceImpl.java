package shop.daijian.seller.service.impl;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.dubbo.config.annotation.Reference;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;
import shop.daijian.common.dto.GoodsBriefDTO;
import shop.daijian.common.dto.ShopBriefDTO;
import shop.daijian.common.dto.ShopSearchDTO;
import shop.daijian.common.enums.ShopCreditEnum;
import shop.daijian.common.exception.BizException;
import shop.daijian.common.form.PageForm;
import shop.daijian.common.form.QueryForm;
import shop.daijian.common.interfaces.GoodsService;
import shop.daijian.common.interfaces.ShopService;
import shop.daijian.common.util.BeanUtil;
import shop.daijian.common.vo.PageVO;
import shop.daijian.seller.entity.ShopInfo;
import shop.daijian.seller.enums.ShopStatusEnum;
import shop.daijian.seller.repository.mybatis.ShopInfoMapper;
import shop.daijian.seller.service.ShopInfoService;


import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author hanshizhou
 * @since 2019-08-05
 **/
@Service
public class ShopServiceImpl implements ShopService {

    @Autowired
    private ShopInfoService shopInfoService;

    @Autowired
    private ShopInfoMapper shopInfoMapper;

    @Reference
    private GoodsService goodsService;

    @Override
    public ShopBriefDTO getShopBrief(String shopId) {
        ShopInfo shopInfo = shopInfoService.getById(shopId);
        if (shopInfo == null) {
            throw new BizException(ShopStatusEnum.SHOP_NOT_EXIST);
        }
        return BeanUtil.transObj(shopInfo, ShopBriefDTO.class);
    }

    @Override
    public List<ShopBriefDTO> listByIdList(List<String> shopIdList, Integer previewGoodsNum) {
        // 通过店铺ID列表获取店铺列表
        Collection<ShopInfo> shopInfos = shopInfoMapper.selectByIdList(shopIdList);
        return shopInfos.stream().map(shopInfo -> {
            // 转换为VO
            ShopBriefDTO shopBriefDTO = BeanUtil.transObj(shopInfo, ShopBriefDTO.class);
            // 填充店铺商品列表
            PageForm notesPageForm = new PageForm(1, previewGoodsNum);
            QueryForm notesQueryForm = new QueryForm("monthly_sales", "desc");
            PageVO<GoodsBriefDTO> goodsBriefDTOPageVO = goodsService.pageShopGoods(shopBriefDTO.getShopId(), notesPageForm, notesQueryForm);
            return shopBriefDTO.setGoodsBriefDTOList(goodsBriefDTOPageVO.getContent());
        }).collect(Collectors.toList());
    }

    @Override
    public void setCredit(String shopId, ShopCreditEnum shopCreditEnum) {
        UpdateWrapper<ShopInfo> wrapper = new UpdateWrapper<>();
        if (shopCreditEnum.isPositive()) {
            wrapper.eq("shop_id", shopId).setSql("credit = credit + " + shopCreditEnum.getScore());
        } else {
            wrapper.eq("shop_id", shopId).setSql("credit = credit - " + shopCreditEnum.getScore());
        }
        shopInfoService.update(wrapper);
    }

    @Override
    public ShopSearchDTO getShopSearchInfo(String shopId) {
        ShopInfo shopInfo = shopInfoService.getById(shopId);
        return BeanUtil.transObj(shopInfo, ShopSearchDTO.class);
    }

    @Override
    public PageVO<ShopSearchDTO> pageShopSearchInfo(PageForm pageForm) {
        IPage<ShopInfo> page = new Page<>(pageForm.getPage(), pageForm.getSize());
        shopInfoService.page(page);
        List<ShopSearchDTO> goodsSearchDTOList = BeanUtil.transList(page.getRecords(), ShopSearchDTO.class);
        return new PageVO<>(page, goodsSearchDTOList);
    }

}
