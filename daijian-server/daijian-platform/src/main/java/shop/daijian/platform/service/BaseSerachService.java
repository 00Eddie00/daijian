package shop.daijian.platform.service;

import java.util.List;

/**
 * @TODO
 * @Author stronghwan
 * @Verison
 * @Date2019/8/13-16-34
 */
public interface BaseSerachService {
    /**
     *  自动补全结果
     * @param prefix 搜索前缀
     * @return
     */
    List<String> suggest(String prefix, String index, Integer size);
}
