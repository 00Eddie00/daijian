package shop.daijian.support.vo;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author liuin
 * @date 2019/8/12 21:26
 */
@Data
@ApiModel(description = "地区信息")
@Accessors(chain = true)
public class RegionInfoVO {

    @ApiModelProperty("地区ID")
    private Integer regionId;

    @ApiModelProperty("地区名")
    private String name;
}



