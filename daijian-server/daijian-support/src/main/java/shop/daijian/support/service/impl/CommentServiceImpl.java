package shop.daijian.support.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;
import shop.daijian.common.dto.CommentDTO;
import shop.daijian.common.enums.CommentTypeEnum;
import shop.daijian.common.exception.BizException;
import shop.daijian.common.form.PageForm;
import shop.daijian.common.form.QueryForm;
import shop.daijian.common.interfaces.CommentService;
import shop.daijian.common.util.BeanUtil;
import shop.daijian.common.util.JsonUtils;
import shop.daijian.common.vo.PageVO;
import shop.daijian.support.entity.CommentInfo;
import shop.daijian.support.entity.CommentView;
import shop.daijian.support.enums.CommentStatusEnum;
import shop.daijian.support.service.CommentInfoService;
import shop.daijian.support.service.CommentViewService;

import java.util.List;
import java.util.stream.Collectors;

import static shop.daijian.common.util.BeanUtil.transObj;


/**
 * <p>
 * 商品评价 服务实现类
 * </p>
 *
 * @author ASUS
 * @since 2019-08-26
 */
@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentInfoService commentInfoService;

    @Autowired
    private CommentViewService commentViewService;

    @Override
    public void saveComment(CommentDTO commentDTO, Boolean canDuplicate) {
        // 当可以有数据并且能重复时
        if (!canDuplicate) {
            QueryWrapper<CommentInfo> wrapper = new QueryWrapper<>();
            wrapper.eq("user_id", commentDTO.getUserId()).eq("unique_id", commentDTO.getUniqueId()).eq("target_type", commentDTO.getCommentTypeEnum());
            int num = commentInfoService.count(wrapper);
            if (num > 0) {
                throw new BizException(CommentStatusEnum.COMMENT_ALREADY_EXIST);
            }
        }
        CommentInfo commentInfo = BeanUtil.transObj(commentDTO, CommentInfo.class);
        commentInfo.setImageUrlJson(JsonUtils.toJson(commentDTO.getImageUrlList()));
        commentInfoService.save(commentInfo);
    }

    @Override
    public PageVO<CommentDTO> pageComment(String targetId, CommentTypeEnum commentTypeEnum, PageForm pageForm, QueryForm queryForm) {
        QueryWrapper<CommentView> wrapper = new QueryWrapper<>();
        wrapper.eq("target_id", targetId).eq("target_type", commentTypeEnum);
        return pageComment(wrapper, pageForm, queryForm);
    }

    @Override
    public PageVO<CommentDTO> pageComment(String targetId, CommentTypeEnum commentTypeEnum, PageForm pageForm, QueryForm queryForm, Integer low, Integer high) {
        QueryWrapper<CommentView> wrapper = new QueryWrapper<>();
        wrapper.eq("target_id", targetId).eq("target_type", commentTypeEnum)
                .ge("star", low).le("star", high);
        return pageComment(wrapper, pageForm, queryForm);
    }

    /**
     * 分页获取评论封装
     */
    private PageVO<CommentDTO> pageComment(QueryWrapper<CommentView> wrapper, PageForm pageForm, QueryForm queryForm) {
        Page<CommentView> page = new Page<>(pageForm.getPage(), pageForm.getSize());
        // 若需要排序
        if (QueryForm.useDefaultSort(queryForm)) {
            wrapper.orderByDesc("create_time");
        } else {
            wrapper.orderBy(true, queryForm.isAsc(), queryForm.getOrderBy());
        }
        commentViewService.page(page, wrapper);
        // 转换为DTO，并填充图册
        List<CommentDTO> commentDTOS = page.getRecords().stream().map(commentView -> {
            CommentDTO commentDTO = transObj(commentView, CommentDTO.class);
            commentDTO.setImageUrlList(JsonUtils.toList(commentView.getImageUrlJson(), String.class));
            return commentDTO;
        }).collect(Collectors.toList());
        return new PageVO<>(page, commentDTOS);
    }

}
