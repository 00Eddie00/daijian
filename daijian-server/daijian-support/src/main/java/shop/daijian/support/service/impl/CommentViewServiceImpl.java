package shop.daijian.support.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.dubbo.config.annotation.Service;
import shop.daijian.support.entity.CommentView;
import shop.daijian.support.mybatis.CommentViewMapper;
import shop.daijian.support.service.CommentViewService;

/**
 * <p>
 * VIEW 服务实现类
 * </p>
 *
 * @author ASUS
 * @since 2019-08-26
 */
@Service
public class CommentViewServiceImpl extends ServiceImpl<CommentViewMapper, CommentView> implements CommentViewService {


}
