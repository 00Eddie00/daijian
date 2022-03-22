package shop.daijian.platform.service.impl;

import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;
import shop.daijian.common.exception.BizException;
import shop.daijian.common.interfaces.CatService;
import shop.daijian.platform.entity.CatBack;
import shop.daijian.platform.enums.CatStatusEnum;
import shop.daijian.platform.service.CatBackService;

import java.util.ArrayList;
import java.util.List;

/**
 * @author liuxin
 * @date 2019/8/8 8:50
 */
@Service
public class CatServiceImpl implements CatService {

    @Autowired
    private CatBackService catBackService;

    @Override
    public String getCatKeyword(String catBackId) {
        List<CatBack> catBacks = catBackService.searchTree(catBackId, new ArrayList<>());
        //如果该分类不存在，则抛出异常
        if (catBacks.size() == 0) {
            throw new BizException(CatStatusEnum.CAT_NOT_EXIST.getMsg());
        }
        List<String> listName = new ArrayList<>();
        for (CatBack catBack : catBacks) {
            String name = catBack.getName();
            listName.add(name);
        }
        //创建StringBuilder对象，用来拼接英文逗号
        StringBuilder str = new StringBuilder();
        for (int i = 0; i < listName.size() - 1; i++) {
            str.append(listName.get(i)).append(",");
        }
        StringBuilder append = str.append(listName.get(listName.size() - 1));
        //将StringBuilder转换为String
        String s = append.toString();
        return s;
    }

}



