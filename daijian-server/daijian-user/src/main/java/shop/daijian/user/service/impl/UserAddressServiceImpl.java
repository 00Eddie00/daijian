package shop.daijian.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.stereotype.Service;
import shop.daijian.common.constant.RegexConstant;
import shop.daijian.common.enums.BaseStatusEnum;
import shop.daijian.common.exception.BizException;
import shop.daijian.common.interfaces.RegionService;
import shop.daijian.common.util.BeanUtil;
import shop.daijian.common.util.MobileUtils;
import shop.daijian.user.entity.UserAddress;
import shop.daijian.user.enums.UserStatusEnum;
import shop.daijian.user.repository.mybatis.UserAddressMapper;
import shop.daijian.user.service.UserAddressService;
import shop.daijian.user.vo.UserAddressVO;


import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 用户收货地址 服务实现类
 * </p>
 *
 * @author suyutong
 * @since 2019-08-04
 */
@Service
public class UserAddressServiceImpl extends ServiceImpl<UserAddressMapper, UserAddress> implements UserAddressService {

    @Reference
    RegionService regionService;

    @Override
    public void saveAddress(UserAddress userAddress) {
        // 如果新地址要设为默认地址，将其他地区都设为非默认
        if (userAddress.getDefaultAddress()) {
            UpdateWrapper<UserAddress> updateWrapper = new UpdateWrapper<>();
            updateWrapper.eq("user_id", userAddress.getUserId()).set("is_default_address", 0);
            update(updateWrapper);
        }
        // 查出完整地区文本
        String region = regionService.getRegion(userAddress.getRegionId());
        userAddress.setRegion(region);
        // 保存
        save(userAddress);
    }

    @Override
    public void updateAddress(UserAddress userAddress) {
        //查出要删除的对象
        UserAddress dbUserAddress = getById(userAddress.getUserAddressId());
        // 为空
        if (dbUserAddress == null) {
            throw new BizException(UserStatusEnum.ADDRESS_NOT_EXIST);
        }
        // 没有权限
        if (!dbUserAddress.getUserId().equals(userAddress.getUserId())) {
            throw new BizException(BaseStatusEnum.NO_PERMISSION);
        }
        // 如果新地址要设为默认地址，将其他地区都设为非默认
        if (userAddress.getDefaultAddress()) {
            UpdateWrapper<UserAddress> updateWrapper = new UpdateWrapper<>();
            updateWrapper.eq("user_id", userAddress.getUserId()).set("is_default_address", 0);
            update(updateWrapper);
        }
        // 查出完整地区文本
        String region = regionService.getRegion(userAddress.getRegionId());
        userAddress.setRegion(region);
        updateById(userAddress);
    }

    @Override
    public void removeAddress(String userId, String userAddressId) {
        //查出要删除的对象
        UserAddress userAddress = getById(userAddressId);
        if (userAddress == null) {
            throw new BizException(UserStatusEnum.ADDRESS_NOT_EXIST);
        }
        if (!userAddress.getUserId().equals(userId)) {
            // 没有权限
            throw new BizException(BaseStatusEnum.NO_PERMISSION);
        }
        removeById(userAddressId);
    }

    @Override
    public UserAddress getDefaultAddress(String userId) {
        QueryWrapper<UserAddress> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", userId).eq("is_default_address", 1);
        return getOne(queryWrapper);
    }

    @Override
    public List<UserAddress> listAddress(String userId) {
        QueryWrapper<UserAddress> wrapper = new QueryWrapper<>();
        wrapper.eq("user_id", userId);
        //查出该用户所有地址
        List<UserAddress> list = list(wrapper);
        //手机号4-7位加密
        for (UserAddress userAddress : list) {
            String num = userAddress.getMobile().replaceAll(RegexConstant.MOBILE_4_TO_7_REGEX, "*");
            userAddress.setMobile(num);
        }
        // 将默认地址置为第一个
        return list.stream().sorted(((o1, o2) -> o1.getDefaultAddress() ? -1 : 0)).collect(Collectors.toList());
    }

    @Override
    public UserAddressVO getAddress(String userAddressId) {
        QueryWrapper<UserAddress> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_address_id", userAddressId);
        UserAddress userAddress = getOne(queryWrapper);
        //手机号4-7位加密
        userAddress.setMobile(MobileUtils.encryptMobile(userAddress.getMobile()));
        return BeanUtil.transObj(userAddress, UserAddressVO.class);
    }
}
