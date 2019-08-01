package com.maple.common.core.bean;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;

@Slf4j
public class BaseRo<T> {
    /**
     * Ro转化为Bean，进行后续业务处理
     *
     * @param clazz
     * @return
     */
    public T toBean(Class<T> clazz) {
        T t = null;
        try {
            t = clazz.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            log.error("Bean NewInstance Error");
        }
        BeanUtils.copyProperties(this, t);
        return t;
    }
}
