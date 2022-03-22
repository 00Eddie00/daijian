package shop.daijian.trade.controller;

import com.alipay.api.AlipayApiException;
import com.alipay.api.internal.util.AlipaySignature;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import shop.daijian.common.exception.BizException;
import shop.daijian.common.support.BaseController;
import shop.daijian.common.wrapper.ResultWrapper;
import shop.daijian.trade.configuration.AlipayProperties;
import shop.daijian.trade.entity.PaymentInfo;
import shop.daijian.trade.enums.AlipayNotifyTypeEnum;
import shop.daijian.trade.enums.PaymentTypeEnum;
import shop.daijian.trade.service.AlipayService;
import shop.daijian.trade.service.PaymentInfoService;
import shop.daijian.trade.util.AlipayUtil;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * 支付宝-电脑网站支付
 *
 * @author guandongwei
 * 2019/8/11
 */
@Api(tags = "支付接口")
@RestController
@Log4j2
@RequestMapping("/alipay")
public class AlipayPagePayController extends BaseController {

    @Autowired
    private AlipayService alipayService;

    @Autowired
    private AlipayProperties alipayProperties;

    @Autowired
    private PaymentInfoService paymentInfoService;

    /**
     * 同步回调地址
     */
    @GetMapping("/returnUrl")
    public String returnUrl(){
        return "支付成功，，，但我不知道现在该去哪";
    }

    @ApiOperation("支付宝异步通知")
    @PostMapping("/notify")
    public String alipayNotify(HttpServletRequest request) {
        log.debug("收到异步请求");
        // 参数Map
        Map<String, String> paramMap = AlipayUtil.getParamMap(request);
        // 检验参数
        try {
            AlipaySignature.rsaCheckV1(paramMap, alipayProperties.getAlipayPublicKey(), alipayProperties.getCharset(), alipayProperties.getSignType());
        } catch (AlipayApiException e) {
            throw new BizException("支付宝验签失败");
        }
        if (paramMap.get("notify_type").equals(AlipayNotifyTypeEnum.TRADE.getCode())) {
            alipayService.finishAlipayTrade(paramMap);
        }
        //填充支付信息
        PaymentInfo paymentInfo = new PaymentInfo();
        paymentInfo.setPaymentId(paramMap.get("out_trade_no"))
                .setTradeNo(paramMap.get("trade_no"))
                .setType("ALIPAY")
                .setOrderIdList(paramMap.get("passback_params"))
                .setTotalPrice(paramMap.get("total_amount"));
        paymentInfoService.save(paymentInfo);
        return "success";
    }


    @ApiOperation("去支付")
    @PostMapping("/gotoPayPage/{paymentTypeEnum}")
    public ResultWrapper gotoPayPage(@RequestHeader String userId,@RequestBody List<String> orderIdList,@PathVariable PaymentTypeEnum paymentTypeEnum){


        //支付模块
        String alipayTradeBody = null;
        if (paymentTypeEnum.equals(PaymentTypeEnum.ALIPAY_PAGE)) {
            //阿里电脑网页支付
            alipayTradeBody = alipayService.createPagePay(orderIdList);
        }
        if (paymentTypeEnum.equals(PaymentTypeEnum.ALIPAY_APP)) {
            //阿里APP支付
            alipayTradeBody = alipayService.createAppPay(orderIdList);
        }
        return ResultWrapper.success(alipayTradeBody);
    }

}
