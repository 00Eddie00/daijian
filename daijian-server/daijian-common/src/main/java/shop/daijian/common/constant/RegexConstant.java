package shop.daijian.common.constant;

/**
 * 正则常量
 *
 * @author qiyubing
 * @since 2019-02-18
 */
public class RegexConstant {

    /**
     * 匹配手机号4-7位
     */
    public static final String MOBILE_4_TO_7_REGEX = "(?<=[\\d]{3})\\d(?=[\\d]{4})";

    /**
     * 合法大陆手机号
     */
    public static final String MOBILE_REGEX = "^1[34578]\\d{9}$";

    /**
     * 合法密码（6-20位字母数字组合）
     */
    public static final String PASSWORD_REGEX = "(?=.*[0-9])(?=.*[a-zA-Z]).{6,20}";

    /**
     * 昵称(2-10位中文或3-10中英文数字混合，且不能为纯数字)
     */
    public static final String NICK_NAME_REGEX = "(?![0-9]+$)[\\u4e00-\\u9fa5_a-zA-Z0-9_]{3,10}|^[\\u4e00-\\u9fa5]{2,10}$";
}
