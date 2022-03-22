package shop.daijian.platform.service;

import shop.daijian.common.dto.NotesSearchDTO;
import shop.daijian.common.form.PageForm;
import shop.daijian.common.form.QueryForm;
import shop.daijian.common.vo.PageVO;
import shop.daijian.platform.entity.Note;

/**
 * @TODO
 * @Author stronghwan
 * @Verison
 * @Date2019/8/13-17-22
 */
public interface NoteSearchService extends BaseSerachService{
    /**
     * 导入店铺数据
     * @return
     */
    Note buildNote(NotesSearchDTO notesSearchDTO);

    /**
     * 搜索商品
     * @param queryForm
     * @return
     */
    PageVO<Note> searchNote(String keyword, QueryForm queryForm, PageForm pageForm);

    /**
     * 增加或修改数据
     * @param notesSearchDTO
     */
    void createDoc(NotesSearchDTO notesSearchDTO);

    /**
     *  删除数据
     * @param id
     */
    void deleteDoc(String id);


    /**
     * 更新店铺搜索补全库
     * @param note
     * @return
     */
    boolean updateNoteSuggest(Note note);
}
