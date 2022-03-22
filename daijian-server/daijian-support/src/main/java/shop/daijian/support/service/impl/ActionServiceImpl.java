package shop.daijian.support.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;
import shop.daijian.common.dto.ActionTraceDTO;
import shop.daijian.common.enums.ActionTypeEnum;
import shop.daijian.common.form.PageForm;
import shop.daijian.common.form.QueryForm;
import shop.daijian.common.interfaces.ActionService;
import shop.daijian.common.vo.PageVO;
import shop.daijian.support.entity.ActionTrace;
import shop.daijian.support.mybatis.ActionTraceMapper;

import java.util.List;

import static shop.daijian.common.util.BeanUtil.transList;

/**
 * @author qiyubing
 * @since 2019-08-14
 */
@Service
public class ActionServiceImpl implements ActionService {

    @Autowired
    private ActionTraceMapper actionTraceMapper;

    @Override
    public Boolean saveActionTrace(String source, String target, ActionTypeEnum actionTypeEnum, Boolean canDuplicate) {
        QueryWrapper<ActionTrace> wrapper = new QueryWrapper<>();
        wrapper.eq("source", source).eq("target", target).eq("type", actionTypeEnum);
        ActionTrace userAction = actionTraceMapper.selectOne(wrapper);
        // 当可以有重复数据或者数据库中没有时新增，否则更新
        if (canDuplicate || userAction == null) {
            userAction = new ActionTrace().setSource(source).setTarget(target).setType(actionTypeEnum);
            actionTraceMapper.insert(userAction);
            return true;
        } else {
            actionTraceMapper.updateById(userAction);
            return false;
        }
    }

    @Override
    public List<ActionTraceDTO> listActionTrace(String source, ActionTypeEnum actionTypeEnum) {
        QueryWrapper<ActionTrace> wrapper = new QueryWrapper<>();
        wrapper.eq("source", source).eq("type", actionTypeEnum);
        List<ActionTrace> actionTraceList = actionTraceMapper.selectList(wrapper);
        return transList(actionTraceList, ActionTraceDTO.class);
    }

    @Override
    public PageVO<ActionTraceDTO> pageActionTrace(String source, ActionTypeEnum actionTypeEnum, PageForm pageForm, QueryForm queryForm) {
        Page<ActionTrace> page = new Page<>(pageForm.getPage(), pageForm.getSize());
        QueryWrapper<ActionTrace> wrapper = new QueryWrapper<>();
        wrapper.eq("source", source).eq("type", actionTypeEnum);
        // 若需要排序
        if (queryForm != null) {
            wrapper.orderBy(true, queryForm.isAsc(), queryForm.getOrderBy());
        }
        actionTraceMapper.selectPage(page, wrapper);
        return new PageVO<>(page, transList(page.getRecords(), ActionTraceDTO.class));
    }

    @Override
    public Boolean existActionTrace(String source, String target, ActionTypeEnum actionTypeEnum) {
        QueryWrapper<ActionTrace> wrapper = new QueryWrapper<>();
        wrapper.eq("source", source).eq("target", target).eq("type", actionTypeEnum);
        return actionTraceMapper.selectCount(wrapper) != 0;
    }

    @Override
    public Boolean removeOne(String source, String target, ActionTypeEnum actionTypeEnum) {
        QueryWrapper<ActionTrace> wrapper = new QueryWrapper<>();
        wrapper.eq("source", source).eq("target", target).eq("type", actionTypeEnum);
        // 批量删除
        return actionTraceMapper.delete(wrapper) == 1;
    }

    @Override
    public Integer remove(String source, List<String> targetList, ActionTypeEnum actionTypeEnum) {
        // TODO 返回影响条数
        QueryWrapper<ActionTrace> wrapper = new QueryWrapper<>();
        wrapper.eq("source", source).eq("type", actionTypeEnum).in("target", targetList);
        return actionTraceMapper.delete(wrapper);
    }

    @Override
    public void removeAll(String source, ActionTypeEnum actionTypeEnum) {
        // TODO 返回影响条数
        QueryWrapper<ActionTrace> wrapper = new QueryWrapper<>();
        wrapper.eq("source", source).eq("type", actionTypeEnum);
        actionTraceMapper.delete(wrapper);
    }

    @Override
    public Integer countType(String source, ActionTypeEnum actionTypeEnum) {
        QueryWrapper<ActionTrace> wrapper = new QueryWrapper<>();
        wrapper.eq("source", source).eq("type", actionTypeEnum);
        return actionTraceMapper.selectCount(wrapper);
    }

    @Override
    public Integer countTarget(String source, String target, ActionTypeEnum actionTypeEnum) {
        QueryWrapper<ActionTrace> wrapper = new QueryWrapper<>();
        wrapper.eq("source", source).eq("target", target).eq("type", actionTypeEnum);
        return actionTraceMapper.selectCount(wrapper);
    }

}
