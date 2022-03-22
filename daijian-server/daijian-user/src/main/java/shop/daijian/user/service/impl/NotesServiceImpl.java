package shop.daijian.user.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;
import shop.daijian.common.dto.NotesBriefDTO;
import shop.daijian.common.dto.NotesSearchDTO;
import shop.daijian.common.form.PageForm;
import shop.daijian.common.interfaces.NotesService;
import shop.daijian.common.util.BeanUtil;
import shop.daijian.common.vo.PageVO;
import shop.daijian.user.entity.NotesInfo;
import shop.daijian.user.repository.mybatis.NotesInfoMapper;
import shop.daijian.user.service.NotesInfoService;

import java.util.List;

/**
 * @author qiyubing
 * @since 2019-08-13
 */
@Service
public class NotesServiceImpl implements NotesService {

    @Autowired
    private NotesInfoService notesInfoService;

    @Autowired
    private NotesInfoMapper notesInfoMapper;

    @Override
    public NotesSearchDTO getNotesSearchInfo(String shopId) {
        NotesInfo shopInfo = notesInfoService.getById(shopId);
        return BeanUtil.transObj(shopInfo, NotesSearchDTO.class);
    }

    @Override
    public PageVO<NotesSearchDTO> pageNotesSearchInfo(PageForm pageForm) {
        IPage<NotesInfo> page = new Page<>(pageForm.getPage(), pageForm.getSize());
        notesInfoService.page(page);
        List<NotesSearchDTO> goodsSearchDTOList = BeanUtil.transList(page.getRecords(), NotesSearchDTO.class);
        return new PageVO<>(page, goodsSearchDTOList);
    }

    @Override
    public List<NotesBriefDTO> listByIdList(List<String> notesIdList) {
        List<NotesInfo> notesInfos = notesInfoMapper.selectByIdList(notesIdList);
        return BeanUtil.transList(notesInfos, NotesBriefDTO.class);
    }
}
