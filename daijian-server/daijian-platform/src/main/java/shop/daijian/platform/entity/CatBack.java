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
 * 商品后台分类
 * </p>
 *
 * @author liuxin
 * @since 2019-08-07
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("cat_back")
public class CatBack extends BaseEntity {

    /**
     * 商品后台分类id
     */
    @TableId(value = "cat_back_id", type = IdType.ID_WORKER_STR)
    private String catBackId;

    /**
     * 分类名称
     */
    @TableField("name")
    private String name;

    /**
     * 父分类id
     */
    @TableField("pid")
    private String pid;

}
