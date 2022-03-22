package shop.daijian.common.interfaces;

import shop.daijian.common.dto.ManorBriefDTO;

import java.util.List;

/**
 * @author qiyubing
 * @since 2019-08-23
 */
public interface ManorService {

    /**
     * 通过庄园主ID列表获取庄园简介列表
     *
     * @param manorUserIdList 庄园主ID列表
     * @param previewNotesNum        预览手记个数
     */
    List<ManorBriefDTO> listByIdList(List<String> manorUserIdList, Integer previewNotesNum);

}
