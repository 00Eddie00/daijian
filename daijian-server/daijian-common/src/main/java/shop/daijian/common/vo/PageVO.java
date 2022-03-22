package shop.daijian.common.vo;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.List;

/**
 * @author qiyubing
 * @since 2019-07-25
 */
@ApiModel(description = "分页查询结果")
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
@Data
public class PageVO<T> implements Serializable {

    private static final long serialVersionUID = -5723444928449828153L;

    @ApiModelProperty("当前页数")
    private Long current;

    @ApiModelProperty("每页的数据条数")
    private Long size;

    @ApiModelProperty("总个数")
    private Long total;

    @ApiModelProperty("查询结果")
    private List<T> content;

    public PageVO(IPage page, List<T> content) {
        this.current = page.getCurrent();
        this.size = page.getSize();
        this.total = page.getTotal();
        this.content = content;
    }

    /**
     * 替换内容
     *
     * @param content 新内容
     * @param <N>     新类型
     * @return 新分页对象
     */
    public <N> PageVO<N> replaceContent(List<N> content) {
        return new PageVO<>(this.getCurrent(), this.getSize(), this.getTotal(), content);
    }

    /**
     * 内容是否为空
     */
    @JsonProperty("empty")
    public boolean isEmpty() {
        return content == null || content.isEmpty();
    }

}
