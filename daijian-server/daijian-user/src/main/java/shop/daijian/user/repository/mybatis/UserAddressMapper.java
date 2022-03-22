package shop.daijian.user.repository.mybatis;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import shop.daijian.user.entity.UserAddress;

/**
 * <p>
 * 用户收货地址 Mapper 接口
 * </p>
 *
 * @author suyutong
 * @since 2019-08-04
 */
@Mapper
public interface UserAddressMapper extends BaseMapper<UserAddress> {

}
