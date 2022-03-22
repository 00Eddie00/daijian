package shop.daijian.platform.vo;

import io.swagger.annotations.ApiModel;
import lombok.Data;

/**
 * @author liuxin
 * @date 2019/8/8 9:13
 */
@ApiModel(description = "前端分类实体")
@Data
public class CatFrontVO {

    /**
     * 前端分类ID
     */
    private String catFrontId;

    /**
     * 分类名
     */
    private String name;

    /**
     * 头像链接
     */
    private String avatarUrl;

    /**
     * 关键词
     */
    private String keyword;

}
