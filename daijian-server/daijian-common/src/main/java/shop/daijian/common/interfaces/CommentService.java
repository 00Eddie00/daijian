package shop.daijian.common.interfaces;

import shop.daijian.common.dto.CommentDTO;
import shop.daijian.common.enums.CommentTypeEnum;
import shop.daijian.common.form.PageForm;
import shop.daijian.common.form.QueryForm;
import shop.daijian.common.vo.PageVO;

/**
 * 评论服务
 *
 * @author liuxin
 * @since 2019/8/26 12:00
 */
public interface CommentService {

    /**
     * 添加评论
     *
     * @param commentDTO   评论 DTO
     * @param canDuplicate 是否重复
     */
    void saveComment(CommentDTO commentDTO, Boolean canDuplicate);

    /**
     * 分页获取评论
     *
     * @param targetId        目标Id
     * @param commentTypeEnum 评论状态枚举
     * @param pageForm        分页表单
     * @param queryForm       检索表单(若默认排序则传null)
     * @return 分页VO
     */
    PageVO<CommentDTO> pageComment(String targetId, CommentTypeEnum commentTypeEnum, PageForm pageForm, QueryForm queryForm);

    /**
     * 分页获取评论
     *
     * @param targetId        目标Id
     * @param commentTypeEnum 评论状态枚举
     * @param pageForm        分页表单
     * @param queryForm       检索表单(若默认排序则传null)
     * @param low             低星
     * @param high            高星
     * @return 分页VO
     */
    PageVO<CommentDTO> pageComment(String targetId, CommentTypeEnum commentTypeEnum, PageForm pageForm, QueryForm queryForm, Integer low, Integer high);

}
