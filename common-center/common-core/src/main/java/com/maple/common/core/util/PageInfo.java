package com.maple.common.core.util;

import lombok.AllArgsConstructor;

import java.util.HashMap;
import java.util.Map;

/**
 * @author zhua
 */
@AllArgsConstructor
public class PageInfo {

    /**
     * 默认页码
     */
    private static final int DEFAULT_PAGE_NUMBER = 1;

    /**
     * 默认每页记录数
     */
    private static final int DEFAULT_PAGE_SIZE = 20;

    /**
     * 页码
     */
    private int current;

    /**
     * 每页记录数
     */
    private int size;

    /**
     * 查询参数Map
     */
    private Map<String, Object> searchMap = new HashMap<String, Object>();


    /**
     * 获取 页码
     *
     * @return 页码
     */
    public int getCurrent() {
        return current < 1 ? DEFAULT_PAGE_NUMBER : current;
    }

    /**
     * 设置 页码
     *
     * @param current 页码
     */
    public void setCurrent(int current) {
        if (current < 1) {
            current = DEFAULT_PAGE_NUMBER;
        }
        this.current = current;
    }

    /**
     * 获取 每页记录数
     *
     * @return 每页记录数
     */
    public int getSize() {
        return size < 1 ? DEFAULT_PAGE_SIZE : size;
    }

    /**
     * 设置 每页记录数
     *
     * @param size 每页记录数
     */
    public void setSize(int size) {
        if (size < 1) {
            size = DEFAULT_PAGE_SIZE;
        }
        this.size = size;
    }

    /**
     * 获取 查询参数Map
     *
     * @return 查询参数Map
     */
    public Map<String, Object> getSearchMap() {
        return searchMap;
    }

    /**
     * 设置 查询参数Map
     *
     * @param searchMap 查询参数Map
     */
    public void setSearchMap(Map<String, Object> searchMap) {
        this.searchMap = searchMap;
    }
}
