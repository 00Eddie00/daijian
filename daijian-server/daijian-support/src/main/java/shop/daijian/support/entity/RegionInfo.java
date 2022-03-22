package shop.daijian.support.entity;

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
 * 地区码表
 * </p>
 *
 * @author liuxin
 * @since 2019-08-12
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("region_info")
public class RegionInfo extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 地区ID
     */
    @TableId(value = "region_id", type = IdType.AUTO)
    private Integer regionId;

    /**
     * 地区父节点
     */
    @TableField("pid")
    private Integer pid;

    /**
     * 行政区划代码
     */
    @TableField("admin_code")
    private String adminCode;

    /**
     * 地区名
     */
    @TableField("name")
    private String name;

    /**
     * 地区级别（1:省份province,2:市city,3:区县district,4:街道street）
     */
    @TableField("level")
    private Integer level;

    /**
     * 区号
     */
    @TableField("area_code")
    private String areaCode;

    /**
     * 城市中心点（即：经纬度坐标）
     */
    @TableField("coordinate")
    private String coordinate;
}
