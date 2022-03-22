package shop.daijian.common.util;

import org.springframework.beans.BeanUtils;
import shop.daijian.common.enums.BaseStatusEnum;
import shop.daijian.common.exception.BizException;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Bean工具类
 *
 * @author qiyubing
 * @since 2019-01-20
 */
public class BeanUtil {

    /**
     * 对象：拷贝属性 转换类型
     *
     * @param source      原对象
     * @param targetClass 目标类型的Class
     * @param <S>         原对象类型
     * @param <T>         目标类型
     * @return 目标对象
     */
    public static <S, T> T transObj(S source, Class<T> targetClass) {
        if (source == null) {
            return null;
        }
        T target;
        try {
            target = targetClass.getConstructor().newInstance();
        } catch (Exception e) {
            throw new BizException(BaseStatusEnum.BEAN_TARNS_ERROR);
        }
        BeanUtils.copyProperties(source, target);
        return target;
    }

    /**
     * 对象List：拷贝属性 转换类型
     *
     * @param sourceList  原对象的List
     * @param targetClass 目标类型的Class
     * @param <S>         原对象类
     * @param <T>         目标类型
     * @return 目标对象的List
     */
    public static <S, T> List<T> transList(Collection<S> sourceList, Class<T> targetClass) {
        if (sourceList.size() == 0) {
            return new ArrayList<>();
        }
        return sourceList.stream()
                .map((source -> transObj(source, targetClass)))
                .collect(Collectors.toList());
    }
}
