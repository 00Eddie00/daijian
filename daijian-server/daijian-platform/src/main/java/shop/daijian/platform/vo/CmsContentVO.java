package shop.daijian.platform.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import shop.daijian.platform.enums.OpenTypeEnum;

/**
 * @author hanshizhou
 * @since 2019-08-26
 **/
@ApiModel(description = "CMS内容")
@Data
public class CmsContentVO {

    @ApiModelProperty("cms内容ID")
    private String cmsContentId;

    @ApiModelProperty("cms页面ID")
    private String cmsSiteId;

    @ApiModelProperty("内容类型")
    private String type;

    @ApiModelProperty("图片ID")
    private String imageUrl;

    @ApiModelProperty("文本1")
    private String text1;

    @ApiModelProperty("文本2")
    private String text2;

    @ApiModelProperty("文本3")
    private String text3;

    @ApiModelProperty("文本4")
    private String text4;

    @ApiModelProperty("链接地址")
    private String link;

    @ApiModelProperty("打开方式")
    private OpenTypeEnum openTypeEnum;

}
