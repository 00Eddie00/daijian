package shop.daijian.common.util;

import static shop.daijian.common.constant.RegexConstant.MOBILE_4_TO_7_REGEX;

/**
 * 手机号工具
 *
 * @author qiyubing
 * @since 2019-02-18
 */
public class MobileUtils {

    /**
     * 将11位手机号4-7位替换为星号
     *
     * @param mobile 11位手机号
     * @return 加密后的手机号
     */
    public static String encryptMobile(String mobile) {
        return mobile.replaceAll(MOBILE_4_TO_7_REGEX, "*");
    }
}
