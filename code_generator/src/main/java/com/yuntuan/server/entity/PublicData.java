package  com.yuntuan.auth.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import  com.yuntuan.server.support.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author DELL
 * @since 2020-03-02
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("public_data")
public class PublicData extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 名称
     */
    @TableField("title")
    private String title;

    @TableField("url")
    private String url;

    /**
     * 来源
     */
    @TableField("origin")
    private String origin;

    /**
     * 描述
     */
    @TableField("article")
    private String article;

    /**
     * 类型
     */
    @TableField("type")
    private String type;

    /**
     * 词条热度
     */
    @TableField("hot")
    private Integer hot;


}
