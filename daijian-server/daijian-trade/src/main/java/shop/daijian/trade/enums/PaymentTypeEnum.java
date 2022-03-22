package shop.daijian.trade.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;


/**
 * @author guandongwei
 * 2019/8/14
 */
public enum PaymentTypeEnum {

    /**
     * 电脑网页支付
     */
    ALIPAY_PAGE,


    /**
     * app支付
     */
    ALIPAY_APP


}
