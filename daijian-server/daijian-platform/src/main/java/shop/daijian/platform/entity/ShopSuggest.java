package shop.daijian.platform.entity;

import lombok.Data;

/**
 * @TODO
 * @Author stronghwan
 * @Verison
 * @Date2019/8/13-10-55
 */
@Data
public class ShopSuggest {
    private String input;
    private Integer weight = 10;
}
