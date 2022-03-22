package shop.daijian.support.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 上传类型枚举类
 *
 * @author liuin
 * @date 2019/8/14
 */
@Getter
@AllArgsConstructor
public enum FileTypeEnum {

    /**
     * 头像
     */
    AVATAR("image/"),

    /**
     * 图片
     */
    IMAGE("image/"),

    /**
     * 文件
     */
    FILE("file/");

    private String dir;

}
