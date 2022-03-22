package shop.daijian.seller.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;
import shop.daijian.common.dto.GoodsBriefDTO;
import shop.daijian.common.dto.GoodsSearchDTO;
import shop.daijian.common.exception.BizException;
import shop.daijian.common.form.PageForm;
import shop.daijian.common.form.QueryForm;
import shop.daijian.common.interfaces.GoodsService;
import shop.daijian.common.util.BeanUtil;
import shop.daijian.common.vo.PageVO;
import shop.daijian.seller.entity.GoodsInfo;
import shop.daijian.seller.enums.GoodsStatusEnum;
import shop.daijian.seller.repository.mybatis.GoodsInfoMapper;
import shop.daijian.seller.service.GoodsInfoService;

import java.util.Collection;
import java.util.List;

import static shop.daijian.common.form.QueryForm.useDefaultSort;
import static shop.daijian.common.util.BeanUtil.transList;


/**
 * @author liuxin
 * @date 2019/8/4 20:34
 */
@Service
public class GoodsServiceImpl implements GoodsService {

    @Autowired
    private GoodsInfoMapper goodsInfoMapper;

    @Autowired
    private GoodsInfoService goodsInfoService;

    @Override
    public GoodsBriefDTO getGoodsBriefById(String goodId) {
        GoodsInfo goodsInfo = goodsInfoMapper.selectById(goodId);
        return BeanUtil.transObj(goodsInfo, GoodsBriefDTO.class);
    }

    @Override
    public PageVO<GoodsSearchDTO> pageGoodsSearch(PageForm pageForm) {
        IPage<GoodsInfo> page = new Page<>(pageForm.getPage(), pageForm.getSize());
        goodsInfoService.page(page);
        List<GoodsSearchDTO> goodsSearchDTOList = BeanUtil.transList(page.getRecords(), GoodsSearchDTO.class);
        return new PageVO<>(page, goodsSearchDTOList);
    }

    @Override
    public GoodsSearchDTO getGoodsSearchById(String goodsId) {
        GoodsInfo goodsInfo = goodsInfoMapper.selectById(goodsId);
        return BeanUtil.transObj(goodsInfo, GoodsSearchDTO.class);
    }

    @Override
    public List<GoodsBriefDTO> listByIdList(List<String> goodsIdList) {
        Collection<GoodsInfo> goodsInfos = goodsInfoMapper.selectByIdList(goodsIdList);
        return BeanUtil.transList(goodsInfos, GoodsBriefDTO.class);
    }

    @Override
    public List<GoodsBriefDTO> listByCatBackId(String catBackId) {
        QueryWrapper<GoodsInfo> wrapper = new QueryWrapper<>();
        wrapper.eq("cat_back_id", catBackId);
        List<GoodsInfo> goodsInfos = goodsInfoMapper.selectList(wrapper);
        return BeanUtil.transList(goodsInfos, GoodsBriefDTO.class);
    }

    @Override
    public void addTotalSales(String goodsId, Integer addNum) {
        GoodsInfo goodsInfo = goodsInfoMapper.selectById(goodsId);
        UpdateWrapper<GoodsInfo> wrapper = new UpdateWrapper<>();
        wrapper.eq("goods_id", goodsId);
        wrapper.setSql("total_sales = total_sales + " + addNum);
        goodsInfoMapper.update(goodsInfo, wrapper);
    }

    @Override
    public PageVO<GoodsBriefDTO> pageShopGoods(String shopId, PageForm pageForm, QueryForm queryForm) {
        QueryWrapper<GoodsInfo> wrapper = new QueryWrapper<>();
        if (useDefaultSort(queryForm)) {
            // 若没有选择排序规则，综合排序
            wrapper.eq("shop_id", shopId).orderByDesc("monthly_sales", "favorable_rate", "total_sales", "comment_num");
        } else {
            wrapper.eq("shop_id", shopId).orderBy(true, queryForm.isAsc(), queryForm.getOrderBy());
        }
        // 分页查询店铺商品
        IPage<GoodsInfo> page = new Page<>(pageForm.getPage(), pageForm.getSize());
        goodsInfoService.page(page, wrapper);
        // 转换类型
        List<GoodsBriefDTO> goodsBriefDTOList = transList(page.getRecords(), GoodsBriefDTO.class);
        return new PageVO<>(page, goodsBriefDTOList);
    }

    @Override
    public PageVO<GoodsBriefDTO> listSaleGoods(PageForm pageForm) {
        IPage<GoodsInfo> page = new Page<>(pageForm.getPage(), pageForm.getSize());
        QueryWrapper<GoodsInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.isNotNull("origin_price");
        goodsInfoService.page(page, queryWrapper);
        // 转换类型
        List<GoodsBriefDTO> goodsBriefDTOList = transList(page.getRecords(), GoodsBriefDTO.class);
        return new PageVO<>(page, goodsBriefDTOList);
    }

    @Override
    public void reduceStock(String goodsId, Integer reduceNum) {
        // ADD LOCK
        GoodsInfo goodsInfo = goodsInfoService.getById(goodsId);

        Integer stockNum = goodsInfo.getStock();
        // 检查库存
        if (stockNum - reduceNum < 0) {
            throw new BizException(GoodsStatusEnum.NO_STOCK);
        }
        UpdateWrapper<GoodsInfo> wrapper = new UpdateWrapper<>();
        wrapper.eq("goods_id", goodsId).set("stock", stockNum - reduceNum);
        goodsInfoMapper.update(goodsInfo, wrapper);
    }

}
