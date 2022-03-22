package shop.daijian.platform.service.impl;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.admin.indices.analyze.AnalyzeAction;
import org.elasticsearch.action.admin.indices.analyze.AnalyzeRequestBuilder;
import org.elasticsearch.action.admin.indices.analyze.AnalyzeResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.stereotype.Service;
import shop.daijian.common.dto.NotesSearchDTO;
import shop.daijian.common.form.PageForm;
import shop.daijian.common.form.QueryForm;
import shop.daijian.common.vo.PageVO;
import shop.daijian.platform.entity.Goods;
import shop.daijian.platform.entity.Note;
import shop.daijian.platform.entity.NoteSuggest;
import shop.daijian.platform.repository.GoodsRepository;
import shop.daijian.platform.repository.NoteRepository;
import shop.daijian.platform.service.BaseSerachService;
import shop.daijian.platform.service.NoteSearchService;
import shop.daijian.platform.units.Dateutils;
import shop.daijian.platform.units.RegularUnits;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @Author stronghwan
 * @Verison
 * @Date2019/8/13-17-36
 */
@Service
@Slf4j
public class NodeSearchServiceImpl extends BaseSearchServiceImpl implements NoteSearchService, BaseSerachService {

    @Autowired
    private NoteRepository noteRepository;

    @Autowired
    private GoodsRepository goodsRepository;

    @Autowired
    private TransportClient transportClient;

    @Override
    public Note buildNote(NotesSearchDTO notesSearchDTO) {
        Note note = new Note();
        Optional<Goods> goodsOptional = goodsRepository.findById(notesSearchDTO.getGoodsId());
        StringBuilder keyword = new StringBuilder();
        keyword.append(notesSearchDTO.getTitle()).append(",")
               .append(notesSearchDTO.getManorName()).append(",")
               .append(goodsOptional.get().getName()).append(",")
               .append(goodsOptional.get().getCategory());
        note.setCommentNum(notesSearchDTO.getCommentNum())
                .setCoverImageUrl(notesSearchDTO.getCoverImageUrl())
                .setCreateTime(Dateutils.toDate(notesSearchDTO.getCreateTime()))
                .setGoodsId(notesSearchDTO.getGoodsId())
                .setKeyword(keyword.toString())
                .setLikeNum(notesSearchDTO.getLikeNum())
                .setManorAvatarUrl(notesSearchDTO.getManorAvatarUrl())
                .setManorName(notesSearchDTO.getManorName())
                .setTitle(notesSearchDTO.getTitle())
                .setUserId(notesSearchDTO.getUserId())
                .setNotesId(notesSearchDTO.getNotesId())
                .setViewNum(notesSearchDTO.getViewNum());
        return note;
    }

    @Override
    public PageVO<Note> searchNote(String keyword, QueryForm queryForm, PageForm pageForm) {
        keyword = checkKeyword(keyword);
        NativeSearchQueryBuilder queryBuilder = new NativeSearchQueryBuilder();
//        queryBuilder.withSourceFilter(
//                new FetchSourceFilter(new String[]{"id","goodsName","avatarUrl","price"},null));
        if (QueryForm.useDefaultSort(queryForm)){
            queryBuilder.withSort(SortBuilders.fieldSort("createTime").order(SortOrder.DESC));
            queryBuilder.withSort(SortBuilders.fieldSort("viewNum").order(SortOrder.DESC));
        }
        sort(keyword,queryForm,queryBuilder,pageForm);
        Page<Note> search = noteRepository.search(queryBuilder.build());
        PageVO<Note> notePageVO = getPageVO(search);
        return notePageVO;
    }

    @Override
    public void createDoc(NotesSearchDTO notesSearchDTO) {
        Note note = buildNote(notesSearchDTO);
        if (!updateNoteSuggest(note)){
            log.debug("【添加或者修改商品没有分词】,goodsId={}",note.getNotesId());
        }
        noteRepository.save(note);
    }

    @Override
    public void deleteDoc(String id) {
        noteRepository.deleteById(id);
    }

    @Override
    public boolean updateNoteSuggest(Note note) {
        AnalyzeRequestBuilder requestBuilder = new AnalyzeRequestBuilder(
                transportClient, AnalyzeAction.INSTANCE, "note", note.getKeyword());
        requestBuilder.setAnalyzer("ik_smart");
        AnalyzeResponse analyzeTokens = requestBuilder.get();
        List<AnalyzeResponse.AnalyzeToken> tokens = analyzeTokens.getTokens();
        if (tokens == null) {
            log.info("【分词出现错误】" + note.getNotesId());
            return false;
        }
        List<NoteSuggest> suggests = new ArrayList<>();
        for (AnalyzeResponse.AnalyzeToken token : tokens) {
            if ("<NUM>".equals(token.getType()) || token.getTerm().length() < 2 || RegularUnits.filterNumAndLer(token.getTerm())) {
                continue;
            }
            NoteSuggest goodsSuggestText = new NoteSuggest();
            goodsSuggestText.setInput(token.getTerm());
            suggests.add(goodsSuggestText);
        }
        NoteSuggest goodsSuggestKeyword = new NoteSuggest();
        goodsSuggestKeyword.setInput(note.getTitle());
        suggests.add(goodsSuggestKeyword);
        note.setSuggests(suggests);
        return true;
    }
}
