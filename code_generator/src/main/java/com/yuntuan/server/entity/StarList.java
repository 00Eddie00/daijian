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
@TableName("star_list")
public class StarList extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @TableField("origin")
    private String origin;

    @TableField("user_id")
    private String userId;

    /**
     * public_data_id

     */
    @TableField("star_id")
    private String starId;


}
