package com.study.redis.utils;

import com.alibaba.fastjson2.JSONArray;
import com.study.redis.constant.CacheConstants;
import com.study.redis.domain.LabelData;
import com.study.redis.domain.LabelTreeSelect;
import com.study.redis.service.RedisService;
import org.apache.commons.lang3.ObjectUtils;

import java.util.Collection;
import java.util.List;

/**
 * 标签工具类
 */
public class LabelUtils
{

    /**
     * 设置标签树缓存
     *
     * @param key 参数键，固定：Label_tree_all:
     * @param treeList 树结构
     */
    public static void setLabelTreeCache(String key, List<LabelTreeSelect> treeList)
    {
        if (ObjectUtils.isEmpty(key)) {
            key = CacheConstants.LABEL_TREE_ALL;
        }
        SpringUtils.getBean(RedisService.class).setCacheObject(getCacheKey(key), treeList);
    }

    /**
     * 设置标签缓存
     * 
     * @param key 参数键 为null， key拼接：label_all:
     * @param dataList 标签数据列表
     */
    public static void setLabelCache(String key, List<LabelData> dataList)
    {
        if (ObjectUtils.isEmpty(key)) {
            key = CacheConstants.LABEL_ALL;
        }
        SpringUtils.getBean(RedisService.class).setCacheObject(getCacheKey(key), dataList);
    }

    /**
     * 设置标签缓存
     *
     * @param key 参数键
     * @param data 标签数据
     */
    public static void setLabelCache(String key, LabelData data)
    {
        SpringUtils.getBean(RedisService.class).setCacheObject(getCacheKey(key), data);
    }


    /**
     * 获取标签树缓存
     * @param key 参数键，固定：label_tree_all:
     * @return
     */
    public static List<LabelTreeSelect> getLabelTreeCache(String key)
    {
        if (ObjectUtils.isEmpty(key)) {
            key = CacheConstants.LABEL_TREE_ALL;
        }
        JSONArray arrayCache = SpringUtils.getBean(RedisService.class).getCacheObject(getCacheKey(key));
        if (StringUtils.isNotNull(arrayCache))
        {
            return arrayCache.toList(LabelTreeSelect.class);
        }
        return null;
    }

    /**
     * 获取标签缓存
     * 
     * @param key 参数键
     * @return DataList 标签数据列表
     */
    public static List<LabelData> getLabelCache(String key)
    {
        if (ObjectUtils.isEmpty(key)) {
            key = CacheConstants.LABEL_ALL;
        }
        JSONArray arrayCache = SpringUtils.getBean(RedisService.class).getCacheObject(getCacheKey(key));
        if (StringUtils.isNotNull(arrayCache))
        {
            return arrayCache.toList(LabelData.class);
        }
        return null;
    }

    /**
     * 获取标签缓存
     *
     * @param key 参数键
     * @return data 标签数据
     */
    public static LabelData getOneLabelCache(String key)
    {
        LabelData data = SpringUtils.getBean(RedisService.class).getCacheObject(getCacheKey(key));
        if (ObjectUtils.isNotEmpty(data))
        {
            return data;
        }
        return null;
    }

    /**
     * 删除指定标签缓存
     * 
     * @param key 字典键
     */
    public static void removeLabelCache(String key)
    {
        SpringUtils.getBean(RedisService.class).deleteObject(getCacheKey(key));
    }

    /**
     * 清空标签缓存
     */
    public static void clearLabelCache()
    {
        Collection<String> keys = SpringUtils.getBean(RedisService.class).keys(CacheConstants.LABEL_KEY + "*");
        SpringUtils.getBean(RedisService.class).deleteObject(keys);
    }

    /**
     * 设置cache key
     * 
     * @param configKey 参数键
     * @return 缓存键key
     */
    public static String getCacheKey(String configKey)
    {
        return CacheConstants.LABEL_KEY + configKey;
    }
}
