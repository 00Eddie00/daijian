package shop.daijian.support.util;


import java.util.HashSet;
import java.util.Set;

/**
 * 文件后缀验证Util
 *
 * @author liuin
 * @date 2019/8/12 21:26
 */
public class FileUtil {

    /**
     * 允许上传的文件后缀名
     */
    private static Set<String> LEGAL_IMAGE_SUFFIX;

    static {
        LEGAL_IMAGE_SUFFIX = new HashSet<>();
        LEGAL_IMAGE_SUFFIX.add(".jpg");
        LEGAL_IMAGE_SUFFIX.add(".jpeg");
        LEGAL_IMAGE_SUFFIX.add(".png");
        LEGAL_IMAGE_SUFFIX.add(".html");
    }

    /**
     * 校验文件的后缀名是否合法
     *
     * @param suffix like png, jpg, jpeg ,txt ,doc and do not bring '.'
     * @return 布尔值.
     */
    public static boolean isSuffixLegal(String suffix) {
        return LEGAL_IMAGE_SUFFIX.contains(suffix);
    }
}



