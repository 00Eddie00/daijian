package shop.daijian.user.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import shop.daijian.user.entity.ManorInfo;
import shop.daijian.user.repository.mybatis.ManorInfoMapper;
import shop.daijian.user.service.ManorInfoService;

/**
 * <p>
 * 庄园信息 服务实现类
 * </p>
 *
 * @author qiyubing
 * @since 2019-08-12
 */
@Service
public class ManorInfoServiceImpl extends ServiceImpl<ManorInfoMapper, ManorInfo> implements ManorInfoService {

}
