package shop.daijian.user.controller;


import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import shop.daijian.common.support.BaseController;
import shop.daijian.common.wrapper.ResultWrapper;
import shop.daijian.user.entity.UserAddress;
import shop.daijian.user.form.UserAddressForm;
import shop.daijian.user.service.UserAddressService;
import shop.daijian.user.vo.UserAddressVO;

import javax.validation.Valid;
import java.util.List;

import static shop.daijian.common.util.BeanUtil.transList;
import static shop.daijian.common.util.BeanUtil.transObj;

/**
 * <p>
 * 用户收货地址 前端控制器
 * </p>
 *
 * @author suyutong
 * @since 2019-08-04
 */
@Api(tags = "用户地址接口")
@RestController
@RequestMapping("/address")
public class UserAddressController extends BaseController {

    @Autowired
    private UserAddressService userAddressService;

    @ApiOperation("查看用户地址列表")
    @GetMapping
    public ResultWrapper listAddress(@RequestHeader String userId) {
        List<UserAddress> userAddressList = userAddressService.listAddress(userId);
        return ResultWrapper.success(transList(userAddressList, UserAddressVO.class));
    }

    @ApiOperation("新增用户地址信息")
    @PostMapping
    public ResultWrapper saveAddress(@RequestHeader String userId, @Valid @RequestBody UserAddressForm userAddressForm, BindingResult bindResult) {
        validateParams(bindResult);
        UserAddress userAddress = transObj(userAddressForm, UserAddress.class);
        userAddress.setUserId(userId);
        userAddressService.saveAddress(userAddress);
        return ResultWrapper.success();
    }

    @ApiOperation("更新用户地址信息")
    @PutMapping("/{userAddressId}")
    public ResultWrapper updateAddress(@RequestHeader String userId, @PathVariable String userAddressId, @Valid @RequestBody UserAddressForm userAddressForm, BindingResult bindResult) {
        validateParams(bindResult);
        UserAddress userAddress = transObj(userAddressForm, UserAddress.class);
        userAddress.setUserAddressId(userAddressId).setUserId(userId);
        userAddressService.updateAddress(userAddress);
        return ResultWrapper.success();
    }

    @ApiOperation("删除用户地址信息")
    @DeleteMapping("/{userAddressId}")
    public ResultWrapper deleteAddress(@RequestHeader String userId, @PathVariable String userAddressId) {
        userAddressService.removeAddress(userId, userAddressId);
        return ResultWrapper.success();
    }

    @ApiOperation("获取用户默认地址")
    @GetMapping("/default")
    public ResultWrapper getDefaultAddress(@RequestHeader String userId) {
        return ResultWrapper.success(transObj(userAddressService.getDefaultAddress(userId), UserAddressVO.class));
    }

}
