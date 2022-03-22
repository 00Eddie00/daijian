package shop.daijian.user.controller;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import shop.daijian.common.dto.ActionTraceDTO;
import shop.daijian.common.dto.ManorBriefDTO;
import shop.daijian.common.enums.ActionTypeEnum;
import shop.daijian.common.exception.BizException;
import shop.daijian.common.form.PageForm;
import shop.daijian.common.interfaces.ActionService;
import shop.daijian.common.interfaces.ManorService;
import shop.daijian.common.support.BaseController;
import shop.daijian.common.vo.PageVO;
import shop.daijian.common.wrapper.ResultWrapper;
import shop.daijian.user.entity.ManorInfo;
import shop.daijian.user.enums.ManorStatusEnum;
import shop.daijian.user.service.ManorInfoService;
import shop.daijian.user.vo.ManorDetailVO;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

import static shop.daijian.common.util.BeanUtil.transObj;

/**
 * <p>
 * 庄园 前端控制器
 * </p>
 *
 * @author qiyubing
 * @since 2019-08-11
 */
@Api(tags = "庄园接口")
@RestController
@RequestMapping
public class ManorController extends BaseController {

    @Autowired
    private ManorInfoService manorInfoService;

    @Autowired(required = false)
    private ManorService manorService;

    @Reference
    private ActionService actionService;

    @ApiOperation("获取庄园详情")
    @GetMapping("/detail/{manorUserId}")
    public ResultWrapper getManorDetail(@ApiParam("庄园主的用户id") @PathVariable String manorUserId, @RequestHeader(required = false) String userId) {
        ManorInfo manorInfo = manorInfoService.getById(manorUserId);
        if (manorInfo == null) {
            throw new BizException(ManorStatusEnum.MANOR_NOT_EXIST.getMsg());
        }
        // 转换类型
        ManorDetailVO manorDetailVO = transObj(manorInfo, ManorDetailVO.class);
        // TODO 增加庄园热度
        // 若用户登录
        if (userId != null) {
            // 是否已关注
            Boolean follow = actionService.existActionTrace(userId, manorUserId, ActionTypeEnum.MANOR_FOLLOW);
            manorDetailVO.setFollow(follow);
            // 增加浏览记录
            actionService.saveActionTrace(userId, manorUserId, ActionTypeEnum.MANOR_VIEW, false);
        }
        return ResultWrapper.success(manorDetailVO);
    }

    @ApiOperation("关注庄园")
    @PostMapping("/follow/{manorUserId}")
    public ResultWrapper follow(@ApiParam("庄园主的用户id") @PathVariable String manorUserId, @RequestHeader String userId) {
        // 判断庄园是否存在
        ManorInfo manorInfo = manorInfoService.getById(manorUserId);
        if (manorInfo == null) {
            throw new BizException(ManorStatusEnum.MANOR_NOT_EXIST.getMsg());
        }
        // 增加关注行为
        Boolean success = actionService.saveActionTrace(userId, manorUserId, ActionTypeEnum.MANOR_FOLLOW, false);
        if (success) {
            // 增加庄园粉丝数 TODO ADD LOCK
            UpdateWrapper<ManorInfo> wrapper = new UpdateWrapper<>();
            wrapper.eq("user_id", manorUserId).setSql("fans_num = fans_num + 1");
            manorInfoService.update(wrapper);
        }
        return ResultWrapper.success();
    }

    @ApiOperation("分页获取用户关注的庄园")
    @GetMapping("/follow")
    public ResultWrapper listUserFollow(@RequestHeader String userId, @Valid PageForm pageForm, @ApiParam("预览手记个数") @RequestParam Integer previewNotesNum, BindingResult bindingResult) {
        validateParams(bindingResult);
        PageVO<ActionTraceDTO> actionTraceDTOPageVO = actionService.pageActionTrace(userId, ActionTypeEnum.MANOR_FOLLOW, pageForm, null);
        // 无关注的庄园
        if (actionTraceDTOPageVO.isEmpty()) {
            return ResultWrapper.success(actionTraceDTOPageVO);
        }
        // 通过行为列表获取庄园主ID列表
        List<String> manorUserIdList = actionTraceDTOPageVO.getContent().stream().map(ActionTraceDTO::getTarget).collect(Collectors.toList());
        // 通过庄园主ID列表获取庄园信息列表
        List<ManorBriefDTO> manorBriefDTOS = manorService.listByIdList(manorUserIdList, previewNotesNum);
        return ResultWrapper.success(actionTraceDTOPageVO.replaceContent(manorBriefDTOS));
    }

    @ApiOperation("通过庄园主ID取消关注")
    @DeleteMapping("/follow/{manorUserId}")
    public ResultWrapper unFollow(@PathVariable String manorUserId, @RequestHeader String userId) {
        // 删除关注行为
        Boolean success = actionService.removeOne(userId, manorUserId, ActionTypeEnum.MANOR_FOLLOW);
        if (success) {
            // 减少庄园粉丝数 TODO ADD LOCK
            UpdateWrapper<ManorInfo> wrapper = new UpdateWrapper<>();
            wrapper.eq("user_id", manorUserId).setSql("fans_num = fans_num - 1");
            manorInfoService.update(wrapper);
        }
        return ResultWrapper.success();
    }

}
