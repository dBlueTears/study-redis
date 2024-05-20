package com.study.redis.controller;

import com.study.redis.customService.ILabelMgtService;
import com.study.redis.domain.LabelTreeSelect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class LabelController {

    @Autowired
    private ILabelMgtService labelMgtService;

    /**
     * 加载标签缓存
     */
    @DeleteMapping("/loadingCache")
    public Boolean loadingCache()
    {
       return labelMgtService.loadingLabelCache();
    }

    /**
     * 清除标签缓存
     */
    @DeleteMapping("/clearCache")
    public Boolean clearCache()
    {
        return labelMgtService.clearLabelCache();
    }

    /**
     * 重置标签缓存
     */
    @DeleteMapping("/refreshCache")
    public Boolean refreshCache()
    {
        return labelMgtService.resetLabelCache();
    }

    /**
     * 获取标签树列表
     */
    @GetMapping("/labelTree")
    public List<LabelTreeSelect> labelTree()
    {
       return labelMgtService.selectLabelTreeList();
    }

}
