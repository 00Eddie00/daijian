package shop.daijian.common.dto;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * <p>
 * 地区码表
 * </p>
 *
 * @author liuxin
 * @since 2019-08-12
 */
@Data
@Accessors(chain = true)
public class RegionDetailInfoDTO implements Serializable {

    private static final long serialVersionUID = 1079302221141847280L;

    /**
     * 地区ID
     */
    private Integer regionId;

    /**
     * 地区父节点
     */
    private Integer pid;

    /**
     * 行政区划代码
     */
    private String adminCode;

    /**
     * 地区名
     */
    private String name;

    /**
     * 地区级别（1:省份province,2:市city,3:区县district,4:街道street）
     */
    private Integer level;

    /**
     * 区号
     */
    private String areaCode;

    /**
     * 城市中心点（即：经纬度坐标）
     */
    private String coordinate;
}
