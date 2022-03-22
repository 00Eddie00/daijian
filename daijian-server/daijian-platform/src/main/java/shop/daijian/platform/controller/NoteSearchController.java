package shop.daijian.platform.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import shop.daijian.common.dto.NotesBriefDTO;
import shop.daijian.common.form.PageForm;
import shop.daijian.common.form.QueryForm;
import shop.daijian.common.interfaces.NotesService;
import shop.daijian.common.support.BaseController;
import shop.daijian.common.util.BeanUtil;
import shop.daijian.common.vo.PageVO;
import shop.daijian.common.wrapper.ResultWrapper;
import shop.daijian.platform.entity.Note;
import shop.daijian.platform.service.NoteSearchService;

import javax.validation.Valid;
import java.util.List;

/**
 * @TODO
 * @Author stronghwan
 * @Verison
 * @Date2019/8/13-18-24
 */

@RestController
@RequestMapping(value = "/search")
@Api(tags = "搜索手记")
public class NoteSearchController extends BaseController {

    @Autowired
    private NoteSearchService noteSearchService;

    @Reference
    private NotesService notesService;

    @ApiOperation("搜索")
    @GetMapping(value = "/note")
    public ResultWrapper<PageVO<NotesBriefDTO>> search(@ApiParam("搜索关键字")@RequestParam(value = "keyword") String keyword,
                                                @Valid QueryForm queryForm,
                                                PageForm pageForm,
                                                BindingResult bindingResult){
        validateParams(bindingResult);
        PageVO<Note> notePage = noteSearchService.searchNote(keyword,queryForm,pageForm);
        List<NotesBriefDTO> noteVOS = BeanUtil.transList(notePage.getContent(), NotesBriefDTO.class);
        PageVO<NotesBriefDTO> noteVOPageVO = new PageVO<>();
        noteVOPageVO.setContent(noteVOS).setSize(notePage.getSize()).setTotal(notePage.getTotal());
        return ResultWrapper.success(noteVOPageVO);
    }

    @ApiOperation("自动补全搜索")
    @GetMapping(value = "/note/{prefix}")
    public ResultWrapper<List<String>> suggest(@PathVariable @ApiParam("搜索前缀") String prefix,
                                               @RequestParam(name = "size")@ApiParam("获取前缀补全数量") Integer size){
        return ResultWrapper.success(noteSearchService.suggest(prefix,"note",size));
    }
}
