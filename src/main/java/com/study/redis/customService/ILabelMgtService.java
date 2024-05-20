package com.study.redis.customService;


import com.study.redis.domain.LabelTreeSelect;

import java.util.List;

public interface ILabelMgtService {


    /**
     * 加载标签缓存数据
     */
    public Boolean loadingLabelCache();

    /**
     * 清空标签缓存数据
     */
    public Boolean clearLabelCache();

    /**
     * 重置标签缓存数据
     */
    public Boolean resetLabelCache();

    /**
     * 获取标签数列表
     * @return
     */
    List<LabelTreeSelect> selectLabelTreeList();
}
