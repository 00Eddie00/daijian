package shop.daijian.platform.units;

import java.util.regex.Pattern;

/**
 * 正则表达式过滤
 * @Author stronghwan
 * @Verison
 * @Date2019/9/2-20-25
 */
public class RegularUnits {
    /**
     * 过滤数字和字母
     * @param input
     * @return
     */
    public static boolean filterNumAndLer(String input){
        String pattern = ".*[0-9].*";
        return  Pattern.matches(pattern, input);
    }
}
