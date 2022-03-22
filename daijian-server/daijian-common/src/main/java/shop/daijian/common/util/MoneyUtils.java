package shop.daijian.common.util;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * 金额工具类
 *
 * @author qiyubing
 * @since 2019-08-13
 */
public class MoneyUtils {

    /**
     * 从单位分转换为单位元
     *
     * @param pennyPrice 价格（单位分）
     * @return 价格（单位元）
     */
    public static BigDecimal toYuan(Integer pennyPrice) {
        return BigDecimal.valueOf(pennyPrice).divide(BigDecimal.valueOf(100L), 2, RoundingMode.UNNECESSARY);
    }

    /**
     * 价格（单位分）* 个数，返回总价（单位元）
     *
     * @param pennyPrice 价格（单位分）
     * @param num        个数
     * @return 总价（单位元）
     */
    public static BigDecimal multiplyToYuan(Integer pennyPrice, Integer num) {
        return toYuan(pennyPrice).multiply(BigDecimal.valueOf(num));
    }

    /**
     * 价格1（单位分）+ 价格2（单位分），返回价格（单位元）
     *
     * @param pennyPrice1 价格1（单位分）
     * @param pennyPrice2 价格2（单位分）
     * @return 价格（单位元）
     */
    public static BigDecimal addToYuan(Integer pennyPrice1, Integer pennyPrice2) {
        return toYuan(pennyPrice1 + pennyPrice2);
    }

    /**
     * 价格1（单位分）- 价格2（单位分），返回价格（单位元）
     *
     * @param pennyPrice1 价格1（单位分）
     * @param pennyPrice2 价格2（单位分）
     * @return 价格（单位元）
     */
    public static BigDecimal subtractToYuan(Integer pennyPrice1, Integer pennyPrice2) {
        return toYuan(pennyPrice1 - pennyPrice2);
    }

}
