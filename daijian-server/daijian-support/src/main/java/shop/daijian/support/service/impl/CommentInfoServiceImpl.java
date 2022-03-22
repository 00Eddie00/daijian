package shop.daijian.support.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import shop.daijian.support.entity.CommentInfo;
import shop.daijian.support.mybatis.CommentInfoMapper;
import shop.daijian.support.service.CommentInfoService;

/**
 * @author qiyubing
 * @since 2019-08-28
 */
@Service
public class CommentInfoServiceImpl extends ServiceImpl<CommentInfoMapper, CommentInfo> implements CommentInfoService {
}
