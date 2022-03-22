package shop.daijian.common.form;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.apache.commons.lang.StringUtils;

import java.io.Serializable;

/**
 * @author qiyubing
 * @since 2019-07-25
 */
@ApiModel(description = "检索方式表单")
@Data
@AllArgsConstructor
public class QueryForm implements Serializable {

    private static final long serialVersionUID = -7496062056344648499L;

    // TODO 之后记得考虑查询安全性，允许哪些排序
    @ApiModelProperty("排序字段")
    private String orderBy;

    @ApiModelProperty("排序方式:desc / asc")
    private String order;

    public Boolean isAsc() {
        return "acs".equals(order);
    }

    /**
     * 是否使用默认排序规则
     *
     * @param queryForm 排序表单
     */
    public static boolean useDefaultSort(QueryForm queryForm) {
        return queryForm == null || StringUtils.isBlank(queryForm.getOrder()) || StringUtils.isBlank(queryForm.getOrderBy());
    }
}
