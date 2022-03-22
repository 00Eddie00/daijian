package shop.daijian.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import shop.daijian.common.dto.NotesBriefDTO;
import shop.daijian.common.form.PageForm;
import shop.daijian.common.form.QueryForm;
import shop.daijian.common.util.BeanUtil;
import shop.daijian.common.vo.PageVO;
import shop.daijian.user.entity.NotesInfo;
import shop.daijian.user.repository.mybatis.NotesInfoMapper;
import shop.daijian.user.service.NotesInfoService;

import java.util.List;

import static shop.daijian.common.form.QueryForm.useDefaultSort;

/**
 * <p>
 * 手记 服务实现类
 * </p>
 *
 * @author qiyubing
 * @since 2019-08-12
 */
@Service
public class NotesInfoServiceImpl extends ServiceImpl<NotesInfoMapper, NotesInfo> implements NotesInfoService {

    @Override
    public PageVO<NotesBriefDTO> pageManorNotes(String manorUserId, PageForm pageForm, QueryForm queryForm) {
        QueryWrapper<NotesInfo> wrapper = new QueryWrapper<>();
        // 是否使用默认排序
        if (useDefaultSort(queryForm)) {
            wrapper.orderByDesc("create_time", "like_num", "view_num", "comment_num");
        } else {
            wrapper.orderBy(true, queryForm.isAsc(), queryForm.getOrderBy());
        }
        // 分页查询庄园主手记
        IPage<NotesInfo> page = new Page<>(pageForm.getPage(), pageForm.getSize());
        wrapper.eq("user_id", manorUserId);
        IPage<NotesInfo> page1 = page(page, wrapper);
        // 转换类型
        List<NotesBriefDTO> notesBriefDTOS = BeanUtil.transList(page1.getRecords(), NotesBriefDTO.class);
        return new PageVO<>(page1, notesBriefDTOS);
    }
}
