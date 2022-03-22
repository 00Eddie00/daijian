package shop.daijian.platform.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import shop.daijian.common.support.BaseEntity;
import shop.daijian.platform.enums.OpenTypeEnum;


/**
 * <p>
 * cms内容
 * </p>
 *
 * @author hanshizhou
 * @since 2019-08-26
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("cms_content")
public class CmsContent extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * cms内容ID
     */
    @TableId(value = "cms_content_id", type = IdType.ID_WORKER_STR)
    private String cmsContentId;

    /**
     * cms页面ID
     */
    @TableField("cms_site_id")
    private String cmsSiteId;

    /**
     * 内容类型
     */
    @TableField("type")
    private String type;

    /**
     * 图片ID
     */
    @TableField("image_url")
    private String imageUrl;

    /**
     * 文本1
     */
    @TableField("text1")
    private String text1;

    /**
     * 文本2
     */
    @TableField("text2")
    private String text2;

    /**
     * 文本3
     */
    @TableField("text3")
    private String text3;

    /**
     * 文本4
     */
    @TableField("text4")
    private String text4;

    /**
     * 链接地址
     */
    @TableField("link")
    private String link;

    /**
     * 打开方式
     */
    @TableField("open_type")
    private OpenTypeEnum openTypeEnum;
    
}
