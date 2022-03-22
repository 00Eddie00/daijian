package shop.daijian.user.service;


import com.baomidou.mybatisplus.extension.service.IService;
import shop.daijian.user.entity.UserAddress;
import shop.daijian.user.vo.UserAddressVO;

import java.util.List;

/**
 * <p>
 * 用户收货地址 服务类
 * </p>
 *
 * @author suyutong
 * @since 2019-08-04
 */
public interface UserAddressService extends IService<UserAddress> {

    /**
     * 创建收货地址
     *
     * @param userAddress
     */
    void saveAddress(UserAddress userAddress);

    /**
     * 修改收货地址
     *
     * @param userAddress
     */
    void updateAddress(UserAddress userAddress);

    /**
     * 删除收获地址
     *
     * @param userId
     * @param userAddressId
     */
    void removeAddress(String userId, String userAddressId);

    /**
     * 获得默认地址
     *
     * @param userId
     * @return 默认地址
     */
    UserAddress getDefaultAddress(String userId);

    /**
     * 获得全部收货地址
     *
     * @param userId
     * @return 全部地址
     */
    List<UserAddress> listAddress(String userId);

    /**
     * 查询用户收货地址
     * @param userAddressId
     * @return
     */
    UserAddressVO getAddress(String userAddressId);

}
