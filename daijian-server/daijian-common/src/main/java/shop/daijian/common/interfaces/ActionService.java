package shop.daijian.common.interfaces;

import shop.daijian.common.dto.ActionTraceDTO;
import shop.daijian.common.enums.ActionTypeEnum;
import shop.daijian.common.form.PageForm;
import shop.daijian.common.form.QueryForm;
import shop.daijian.common.vo.PageVO;

import java.util.List;

/**
 * <p>
 * 行为记录 服务类
 * </p>
 *
 * @author qiyubing
 * @since 2019-08-14
 */
public interface ActionService {

    /**
     * 保存行为记录，若选择不许重复，当重复时会更新update_time
     *
     * @param source         发起者
     * @param target         目标
     * @param actionTypeEnum 行为类型枚举
     * @param canDuplicate   是否可重复
     * @return isSuccess
     */
    Boolean saveActionTrace(String source, String target, ActionTypeEnum actionTypeEnum, Boolean canDuplicate);

    /**
     * 查询行为记录列表
     *
     * @param source         发起者
     * @param actionTypeEnum 行为类型枚举
     */
    List<ActionTraceDTO> listActionTrace(String source, ActionTypeEnum actionTypeEnum);

    /**
     * 分页查询行为记录列表
     *
     * @param source         发起者
     * @param actionTypeEnum 行为类型枚举
     * @param pageForm       分页表单
     * @param queryForm      检索表单(若默认排序则传null)
     */
    PageVO<ActionTraceDTO> pageActionTrace(String source, ActionTypeEnum actionTypeEnum, PageForm pageForm, QueryForm queryForm);

    /**
     * 判断行为记录是否存在
     *
     * @param source         发起者
     * @param target         目标
     * @param actionTypeEnum 行为类型枚举
     */
    Boolean existActionTrace(String source, String target, ActionTypeEnum actionTypeEnum);

    /**
     * 删除单个行为记录
     *
     * @param source         发起者
     * @param target         目标
     * @param actionTypeEnum 行为类型枚举
     * @return isSuccess
     */
    Boolean removeOne(String source, String target, ActionTypeEnum actionTypeEnum);

    /**
     * 删除多个行为记录
     *
     * @param source     发起者
     * @param targetList 目标List
     * @return affectNum
     */
    Integer remove(String source, List<String> targetList, ActionTypeEnum actionTypeEnum);

    /**
     * 删除全部行为记录
     *
     * @param source         发起者
     * @param actionTypeEnum 行为类型枚举
     */
    void removeAll(String source, ActionTypeEnum actionTypeEnum);

    /**
     * 统计发起者行为类型个数
     *
     * @param source         发起者
     * @param actionTypeEnum 行为类型枚举
     * @return 个数
     */
    Integer countType(String source, ActionTypeEnum actionTypeEnum);

    /**
     * 统计发起者目标个数
     *
     * @param source         发起者
     * @param target         目标
     * @param actionTypeEnum 行为类型枚举
     * @return 个数
     */
    Integer countTarget(String source, String target, ActionTypeEnum actionTypeEnum);
}
