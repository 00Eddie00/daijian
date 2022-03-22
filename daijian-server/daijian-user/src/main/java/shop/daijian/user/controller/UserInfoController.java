package shop.daijian.user.controller;


import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import shop.daijian.common.enums.ActionTypeEnum;
import shop.daijian.common.exception.BizException;
import shop.daijian.common.interfaces.ActionService;
import shop.daijian.common.support.BaseController;
import shop.daijian.common.wrapper.ResultWrapper;
import shop.daijian.user.entity.UserInfo;
import shop.daijian.user.enums.UserStatusEnum;
import shop.daijian.user.form.UserInfoForm;
import shop.daijian.user.service.UserInfoService;
import shop.daijian.user.vo.UserDetailVO;

import javax.validation.Valid;

import static shop.daijian.common.util.BeanUtil.transObj;


/**
 * <p>
 * 用户信息 前端控制器
 * </p>
 *
 * @author suyutong
 * @since 2019-08-04
 */
@Api(tags = "用户信息接口")
@RestController
@RequestMapping("/info")
public class UserInfoController extends BaseController {

    @Autowired
    private UserInfoService userInfoService;

    @Reference
    private ActionService actionService;

    @ApiOperation("获取用户信息")
    @GetMapping
    public ResultWrapper getUserInfo(@RequestHeader String userId) {
        UserInfo userInfo = userInfoService.getById(userId);
        if (userInfo == null) {
            throw new BizException(UserStatusEnum.USER_NOT_EXIST);
        }
        UserDetailVO userDetailVO = transObj(userInfo, UserDetailVO.class);
        Integer goodsFavoriteNum = actionService.countType(userId, ActionTypeEnum.GOODS_FAVORITE);
        userDetailVO.setFavoriteNum(goodsFavoriteNum);
        Integer manorFollowNum = actionService.countType(userId, ActionTypeEnum.MANOR_FOLLOW);
        userDetailVO.setFollowNum(manorFollowNum);
        return ResultWrapper.success(userDetailVO);
    }

    @ApiOperation("修改用户信息")
    @PutMapping
    public ResultWrapper updateUserInfo(@RequestHeader String userId, @Valid @RequestBody UserInfoForm userInfoForm, BindingResult bindResult) {
        validateParams(bindResult);
        UserInfo userInfo = transObj(userInfoForm, UserInfo.class);
        userInfo.setUserId(userId);
        userInfoService.updateById(userInfo);
        return ResultWrapper.success();
    }

}
