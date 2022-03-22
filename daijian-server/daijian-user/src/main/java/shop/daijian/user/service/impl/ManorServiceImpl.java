package shop.daijian.user.service.impl;

import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;
import shop.daijian.common.dto.ManorBriefDTO;
import shop.daijian.common.dto.NotesBriefDTO;
import shop.daijian.common.form.PageForm;
import shop.daijian.common.form.QueryForm;
import shop.daijian.common.interfaces.ManorService;
import shop.daijian.common.util.BeanUtil;
import shop.daijian.common.vo.PageVO;
import shop.daijian.user.entity.ManorInfo;
import shop.daijian.user.repository.mybatis.ManorInfoMapper;
import shop.daijian.user.service.NotesInfoService;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author qiyubing
 * @since 2019-08-23
 */
@Service
public class ManorServiceImpl implements ManorService {

    @Autowired
    private ManorInfoMapper manorInfoMapper;

    @Autowired
    private NotesInfoService notesInfoService;

    @Override
    public List<ManorBriefDTO> listByIdList(List<String> manorUserIdList, Integer previewNotesNum) {
        // 通过庄园ID列表获取庄园列表
        Collection<ManorInfo> manorInfoList = manorInfoMapper.selectByIdList(manorUserIdList);
        return manorInfoList.stream().map(manorInfo -> {
            // 转换为VO
            ManorBriefDTO manorBriefDTO = BeanUtil.transObj(manorInfo, ManorBriefDTO.class);
            // 填充庄园手记列表
            PageForm notesPageForm = new PageForm(1, previewNotesNum);
            QueryForm notesQueryForm = new QueryForm("create_time", "desc");
            PageVO<NotesBriefDTO> notesBriefVOPageVO = notesInfoService.pageManorNotes(manorBriefDTO.getUserId(), notesPageForm, notesQueryForm);
            manorBriefDTO.setNotesBriefDTOList(notesBriefVOPageVO.getContent());
            return manorBriefDTO;
        }).collect(Collectors.toList());
    }

}
