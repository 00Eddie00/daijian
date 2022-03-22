package shop.daijian.common.interfaces;

import shop.daijian.common.dto.NotesBriefDTO;
import shop.daijian.common.dto.NotesSearchDTO;
import shop.daijian.common.form.PageForm;
import shop.daijian.common.vo.PageVO;

import java.util.List;

/**
 * 手记服务接口
 *
 * @author qiyubing
 * @since 2019-08-13
 */
public interface NotesService {

    /**
     * 获取手记搜索信息
     */
    NotesSearchDTO getNotesSearchInfo(String shopId);

    /**
     * 分页获取手记搜索信息
     */
    PageVO<NotesSearchDTO> pageNotesSearchInfo(PageForm pageForm);

    /**
     * 通过手记ID列表查询手记信息列表（保证顺序，但不保证缺失）
     */
    List<NotesBriefDTO> listByIdList(List<String> notesIdList);
}
