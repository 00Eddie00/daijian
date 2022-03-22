package shop.daijian.user.service.impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import shop.daijian.common.dto.UserAddressDTO;
import shop.daijian.common.dto.UserBriefDTO;
import shop.daijian.common.exception.BizException;
import shop.daijian.common.util.BeanUtil;
import shop.daijian.user.entity.UserInfo;
import shop.daijian.user.enums.UserStatusEnum;
import shop.daijian.user.service.UserAddressService;
import shop.daijian.user.service.UserInfoService;
import shop.daijian.user.service.UserService;

import static shop.daijian.common.util.BeanUtil.transObj;

/**
 * @author suyutong
 * @since 2019/8/10 10:33
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserInfoService userInfoService;

    @Autowired
    UserAddressService userAddressService;

    @Override
    public UserBriefDTO getUserBrief(String userId) {
        UserInfo userInfo = userInfoService.getById(userId);
        if (userInfo == null) {
            throw new BizException(UserStatusEnum.USER_NOT_EXIST);
        }
        return transObj(userInfo, UserBriefDTO.class);
    }

    @Override
    public void createNewUser(String userId) {
        UserInfo userInfo = new UserInfo();
        userInfo.setUserId(userId);
        userInfo.setNickName("用户" + userId.substring(0, 6));
        userInfoService.save(userInfo);
    }

    @Override
    public UserAddressDTO getAddress(String userAddressId) {
        return BeanUtil.transObj(userAddressService.getAddress(userAddressId), UserAddressDTO.class);
    }

}
