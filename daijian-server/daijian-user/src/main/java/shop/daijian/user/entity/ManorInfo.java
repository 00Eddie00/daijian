package shop.daijian.user.entity;

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
 * 庄园信息
 * </p>
 *
 * @author qiyubing
 * @since 2019-08-12
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("manor_info")
public class ManorInfo extends BaseEntity {

    /**
     * 用户ID
     */
    @TableId(value = "user_id", type = IdType.ID_WORKER_STR)
    private String userId;

    /**
     * 庄园名
     */
    @TableField("name")
    private String name;

    /**
     * logo链接
     */
    @TableField("avatar_url")
    private String avatarUrl;

    /**
     * 介绍
     */
    @TableField("introduction")
    private String introduction;

    /**
     * 粉丝数
     */
    @TableField("fans_num")
    private Integer fansNum;

    /**
     * 手记总数
     */
    @TableField("notes_num")
    private Integer notesNum;

    /**
     * 热度
     */
    @TableField("hot_num")
    private Integer hotNum;

}
