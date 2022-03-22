package shop.daijian.platform.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import shop.daijian.common.support.BaseEntity;

/**
 * <p>
 * 前端分类
 * </p>
 *
 * @author liuxin
 * @since 2019-08-07
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("cat_front")
public class CatFront extends BaseEntity {

    /**
     * 前端分类ID
     */
    @TableId(value = "cat_front_id", type = IdType.ID_WORKER_STR)
    private String catFrontId;

    /**
     * 分类名
     */
    @TableField("name")
    private String name;

    /**
     * 关键词
     */
    @TableField("keyword")
    private String keyword;

    /**
     * 头像链接
     */
    @TableField("avatar_url")
    private String avatarUrl;

    /**
     * 父分类id
     */
    @TableField("pid")
    private String pid;

}
