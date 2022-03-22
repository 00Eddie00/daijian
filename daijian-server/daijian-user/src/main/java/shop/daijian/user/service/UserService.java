package shop.daijian.user.service;

import shop.daijian.common.dto.UserAddressDTO;
import shop.daijian.common.dto.UserBriefDTO;

/**
 * <p>
 * 用户信息 服务类
 * </p>
 *
 * @author suyutong
 * @since 2019-08-04
 */
public interface UserService {

    /**
     * 获取用户简要信息
     */
    UserBriefDTO getUserBrief(String userId);

    /**
     * 新增用户信息
     *
     * @param userId 用户ID
     */
    void createNewUser(String userId);

    /**
     * 查询用户收货地址
     */
    UserAddressDTO getAddress(String userAddressId);

}
