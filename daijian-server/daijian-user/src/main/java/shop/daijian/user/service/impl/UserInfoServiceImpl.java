package shop.daijian.user.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import shop.daijian.user.entity.UserInfo;
import shop.daijian.user.service.UserInfoService;
import shop.daijian.user.repository.mybatis.UserInfoMapper;

/**
 * <p>
 * 用户信息 服务实现类
 * </p>
 *
 * @author suyutong
 * @since 2019-08-04
 */
@Service
public class UserInfoServiceImpl extends ServiceImpl<UserInfoMapper, UserInfo> implements UserInfoService {

}
