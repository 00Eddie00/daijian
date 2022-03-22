package shop.daijian.user.service;

import com.baomidou.mybatisplus.extension.service.IService;
import shop.daijian.common.dto.NotesBriefDTO;
import shop.daijian.common.form.PageForm;
import shop.daijian.common.form.QueryForm;
import shop.daijian.common.vo.PageVO;
import shop.daijian.user.entity.NotesInfo;

/**
 * <p>
 * 手记 服务类
 * </p>
 *
 * @author qiyubing
 * @since 2019-08-12
 */
public interface NotesInfoService extends IService<NotesInfo> {

    /**
     * 分页获取庄园手记列表
     */
    PageVO<NotesBriefDTO> pageManorNotes(String manorUserId, PageForm pageForm, QueryForm queryForm);
}
