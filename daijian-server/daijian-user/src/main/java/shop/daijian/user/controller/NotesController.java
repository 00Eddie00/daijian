package shop.daijian.user.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import shop.daijian.common.dto.CommentDTO;
import shop.daijian.common.dto.GoodsBriefDTO;
import shop.daijian.common.dto.NotesBriefDTO;
import shop.daijian.common.enums.ActionTypeEnum;
import shop.daijian.common.enums.BaseStatusEnum;
import shop.daijian.common.enums.CommentTypeEnum;
import shop.daijian.common.exception.BizException;
import shop.daijian.common.form.PageForm;
import shop.daijian.common.form.QueryForm;
import shop.daijian.common.interfaces.ActionService;
import shop.daijian.common.interfaces.CommentService;
import shop.daijian.common.interfaces.GoodsService;
import shop.daijian.common.support.BaseController;
import shop.daijian.common.util.BeanUtil;
import shop.daijian.common.vo.PageVO;
import shop.daijian.common.wrapper.ResultWrapper;
import shop.daijian.user.entity.ManorInfo;
import shop.daijian.user.entity.NotesInfo;
import shop.daijian.user.enums.ManorStatusEnum;
import shop.daijian.user.enums.NotesStatusEnum;
import shop.daijian.user.form.NotesCommentForm;
import shop.daijian.user.form.NotesForm;
import shop.daijian.user.service.ManorInfoService;
import shop.daijian.user.service.NotesInfoService;
import shop.daijian.user.vo.ManorDetailVO;
import shop.daijian.user.vo.NotesCommentVO;
import shop.daijian.user.vo.NotesDetailVO;

import javax.validation.Valid;
import java.util.List;

import static shop.daijian.common.form.QueryForm.useDefaultSort;
import static shop.daijian.common.util.BeanUtil.transList;
import static shop.daijian.common.util.BeanUtil.transObj;

/**
 * <p>
 * ?????? ???????????????
 * </p>
 *
 * @author qiyubing
 * @since 2019-08-11
 */
@Api(tags = "????????????")
@RestController
@RequestMapping("/notes")
public class NotesController extends BaseController {

    @Autowired
    private NotesInfoService notesInfoService;

    @Autowired
    private ManorInfoService manorInfoService;

    @Reference
    private GoodsService goodsService;

    @Reference
    private ActionService actionService;

    @Reference
    private CommentService commentService;

    @ApiOperation("????????????")
    @PostMapping
    public ResultWrapper createNotes(@RequestHeader String userId, @Valid @RequestBody NotesForm form, BindingResult bindingResult) {
        validateParams(bindingResult);
        // ?????????????????????
        NotesInfo notesInfo = transObj(form, NotesInfo.class);
        notesInfo.setUserId(userId);
        // ??????????????????
        ManorInfo manorInfo = manorInfoService.getById(userId);
        // ???????????????
        if (manorInfo == null) {
            throw new BizException(ManorStatusEnum.MANOR_NOT_EXIST.getMsg());
        }
        notesInfo.setManorName(manorInfo.getName());
        notesInfo.setManorAvatarUrl(manorInfo.getAvatarUrl());
        // ??????
        notesInfoService.save(notesInfo);
        // ???????????????????????? TODO ADD LOCK
        UpdateWrapper<ManorInfo> wrapper = new UpdateWrapper<>();
        wrapper.eq("user_id", userId).setSql("note_num = note_num + 1");
        manorInfoService.update(wrapper);
        return ResultWrapper.success();
    }

    @ApiOperation("????????????")
    @PutMapping("/{notesId}")
    public ResultWrapper updateNotes(@RequestHeader String userId, @PathVariable String notesId,
                                     @Valid @RequestBody NotesForm form, BindingResult bindingResult) {
        // ????????????
        NotesInfo userNotes = notesInfoService.getById(notesId);
        // ???????????????
        if (userNotes == null) {
            throw new BizException(NotesStatusEnum.NOTES_NOT_EXIST);
        }
        // ????????????
        if (!userNotes.getUserId().equals(userId)) {
            throw new BizException(BaseStatusEnum.NO_PERMISSION);
        }
        // ??????
        UpdateWrapper<NotesInfo> wrapper = new UpdateWrapper<>();
        wrapper.eq("note_id", notesId)
                .set("cover_image_url", form.getCoverImageUrl())
                .set("title", form.getTitle())
                .set("content", form.getContent());
        notesInfoService.update(wrapper);
        return ResultWrapper.success();
    }

    @ApiOperation("??????????????????")
    @GetMapping("/detail/{notesId}")
    public ResultWrapper<NotesDetailVO> getNotesDetail(@PathVariable String notesId, @RequestHeader(required = false) String userId) {
        NotesInfo userNotes = notesInfoService.getById(notesId);
        // ???????????????
        if (userNotes == null) {
            throw new BizException(NotesStatusEnum.NOTES_NOT_EXIST);
        }
        // ??????????????????
        NotesDetailVO userNotesDetailVO = transObj(userNotes, NotesDetailVO.class);
        // ???????????????????????????
        GoodsBriefDTO goodsBriefDTO = goodsService.getGoodsBriefById(userNotes.getGoodsId());
        userNotesDetailVO.setGoodsBriefDTO(goodsBriefDTO);
        // ??????????????????
        ManorInfo userManor = manorInfoService.getById(userNotes.getUserId());
        ManorDetailVO manorDetailVO = transObj(userManor, ManorDetailVO.class);
        userNotesDetailVO.setManorDetailVO(manorDetailVO);
        // ???????????????
        if (userId != null) {
            // ?????????????????????
            Boolean like = actionService.existActionTrace(userId, notesId, ActionTypeEnum.NOTES_LIKE);
            userNotesDetailVO.setLike(like);
            // ?????????????????????
            Boolean follow = actionService.existActionTrace(userId, userNotes.getUserId(), ActionTypeEnum.MANOR_FOLLOW);
            manorDetailVO.setFollow(follow);
            // ??????????????????
            Boolean success = actionService.saveActionTrace(userId, notesId, ActionTypeEnum.NOTES_VIEW, false);
            if (success) {
                // ??????????????? TODO ADD LOCK
                UpdateWrapper<NotesInfo> wrapper = new UpdateWrapper<>();
                wrapper.eq("note_id", notesId).setSql("view_num = view_num + 1");
                notesInfoService.update(wrapper);
            }
        }
        return ResultWrapper.success(userNotesDetailVO);
    }

    @ApiOperation("??????????????????")
    @GetMapping("/my")
    public ResultWrapper pageMyNotes(@RequestHeader String userId, @Valid PageForm pageForm, @Valid QueryForm queryForm, BindingResult bindingResult) {
        validateParams(bindingResult);
        return ResultWrapper.success(notesInfoService.pageManorNotes(userId, pageForm, queryForm));
    }

    @ApiOperation("??????????????????")
    @GetMapping("/{notesId}")
    public ResultWrapper removeNotes(@RequestHeader String userId, @PathVariable String notesId) {
        NotesInfo notesInfo = notesInfoService.getById(notesId);
        // ???????????????
        if (notesInfo == null) {
            throw new BizException(NotesStatusEnum.NOTES_NOT_EXIST);
        }
        // ????????????
        if (!notesInfo.getUserId().equals(userId)) {
            throw new BizException(BaseStatusEnum.NO_PERMISSION);
        }
        boolean success = notesInfoService.removeById(notesId);
        if (success) {
            // ???????????????????????? TODO ADD LOCK
            UpdateWrapper<ManorInfo> wrapper = new UpdateWrapper<>();
            wrapper.eq("user_id", userId).setSql("note_num = note_num - 1");
            manorInfoService.update(wrapper);
        }
        return ResultWrapper.success();
    }

    @ApiOperation("????????????????????????")
    @GetMapping("/{manorUserId}")
    public ResultWrapper pageManorNotes(@ApiParam("??????????????????id") @PathVariable String manorUserId, @Valid PageForm pageForm, @Valid QueryForm queryForm, BindingResult bindingResult) {
        validateParams(bindingResult);
        return ResultWrapper.success(notesInfoService.pageManorNotes(manorUserId, pageForm, queryForm));
    }

    @ApiOperation("??????????????????????????????")
    @GetMapping("/platform")
    public ResultWrapper pagePlatformNotes(@Valid PageForm pageForm, @Valid QueryForm queryForm, BindingResult bindingResult) {
        validateParams(bindingResult);
        // ???????????????????????????
        IPage<NotesInfo> page = new Page<>(pageForm.getPage(), pageForm.getSize());
        QueryWrapper<NotesInfo> wrapper = new QueryWrapper<>();
        // ????????????????????????
        if (useDefaultSort(queryForm)) {
            wrapper.orderByDesc("create_time", "like_num", "view_num", "comment_num");
        } else {
            wrapper.orderBy(true, queryForm.isAsc(), queryForm.getOrderBy());
        }
        notesInfoService.page(page, wrapper);
        // ????????????VO
        List<NotesBriefDTO> userNotesBriefDTOList = BeanUtil.transList(page.getRecords(), NotesBriefDTO.class);
        PageVO<NotesBriefDTO> pageVO = new PageVO<>(page, userNotesBriefDTOList);
        return ResultWrapper.success(pageVO);
    }

    @ApiOperation("??????")
    @PostMapping("/like/{notesId}")
    public ResultWrapper like(@RequestHeader String userId, @PathVariable String notesId) {
        // ????????????????????????
        NotesInfo notesInfo = notesInfoService.getById(notesId);
        if (notesInfo == null) {
            throw new BizException(NotesStatusEnum.NOTES_NOT_EXIST);
        }
        // ??????????????????
        Boolean success = actionService.saveActionTrace(userId, notesId, ActionTypeEnum.NOTES_LIKE, false);
        if (success) {
            // ??????????????? TODO ADD LOCK
            UpdateWrapper<NotesInfo> wrapper = new UpdateWrapper<>();
            wrapper.eq("note_id", notesId).setSql("like_num = like_num + 1");
            notesInfoService.update(wrapper);
        }
        return ResultWrapper.success();
    }

    @ApiOperation("????????????")
    @DeleteMapping("/like/{notesId}")
    public ResultWrapper unLike(@RequestHeader String userId, @PathVariable String notesId) {
        // ??????????????????
        Boolean success = actionService.removeOne(userId, notesId, ActionTypeEnum.NOTES_LIKE);
        if (success) {
            // ??????????????? TODO ADD LOCK
            UpdateWrapper<NotesInfo> wrapper = new UpdateWrapper<>();
            wrapper.eq("note_id", notesId).setSql("like_num = like_num - 1");
            notesInfoService.update(wrapper);
        }
        return ResultWrapper.success();
    }

    @ApiOperation("?????????????????????????????????")
    @GetMapping("/comment/{notesId}")
    public ResultWrapper<PageVO<NotesCommentVO>> pageComment(@ApiParam("??????id") @PathVariable String notesId, @Valid PageForm pageForm,
                                                             @Valid QueryForm queryForm, BindingResult bindingResult) {
        validateParams(bindingResult);
        PageVO<CommentDTO> commentDTOPageVO = commentService.pageComment(notesId, CommentTypeEnum.NOTES, pageForm, queryForm);
        List<NotesCommentVO> notesCommentVOS = transList(commentDTOPageVO.getContent(), NotesCommentVO.class);
        return ResultWrapper.success(commentDTOPageVO.replaceContent(notesCommentVOS));
    }

    @ApiOperation("??????????????????")
    @PostMapping("/comment")
    public ResultWrapper addComment(@RequestHeader String userId, @Valid @RequestBody NotesCommentForm notesCommentForm, BindingResult bindingResult) {
        validateParams(bindingResult);
        // ????????????DTO
        CommentDTO commentDTO = BeanUtil.transObj(notesCommentForm, CommentDTO.class);
        commentDTO.setUserId(userId)
                .setTargetId(notesCommentForm.getNotesId())
                .setCommentTypeEnum(CommentTypeEnum.NOTES);
        // ????????????
        commentService.saveComment(commentDTO, true);
        // ?????????????????????
        UpdateWrapper<NotesInfo> wrapper = new UpdateWrapper<>();
        wrapper.eq("note_id", notesCommentForm.getNotesId()).setSql("comment_num = comment_num + 1");
        notesInfoService.update(wrapper);
        return ResultWrapper.success();
    }

}
