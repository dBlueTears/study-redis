package com.study.redis.customService.impl;

import com.study.redis.customService.ILabelMgtService;
import com.study.redis.domain.LabelData;
import com.study.redis.domain.LabelTreeSelect;
import com.study.redis.utils.LabelUtils;
import com.study.redis.utils.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class LabelMgtService implements ILabelMgtService {

    /**
     * 项目启动时，初始化标签到缓存
     */
    @PostConstruct
    public void init()
    {
        loadingLabelCache();
    }

    /**
     * 存储key值为标签
     * 存储标签树结构
     *
     * 注意：此处模拟数据
     */
    @Override
    public Boolean loadingLabelCache() {
        List<LabelData> dataList = dataInfoList();
        //存储标签列表
        LabelUtils.setLabelCache(null, dataList);
        for (LabelData data: dataList) {
            //存储单条标签
            LabelUtils.setLabelCache(String.valueOf(data.getId()), data);
        }
        //存储树结构标签
        List<LabelTreeSelect> treeList = buildLabelTreeSelect(dataList);
        LabelUtils.setLabelTreeCache(null, treeList);
        return Boolean.TRUE;
    }

    @Override
    public Boolean clearLabelCache() {
        LabelUtils.clearLabelCache();
        return Boolean.TRUE;
    }

    @Override
    public Boolean resetLabelCache() {
        clearLabelCache();
        loadingLabelCache();
        return Boolean.TRUE;
    }

    @Override
    public List<LabelTreeSelect> selectLabelTreeList() {
        List<LabelTreeSelect> treeList = LabelUtils.getLabelTreeCache(null);
        return treeList;
    }

    /**
     * 构建下拉树结构(主要作用：封装树结构)
     *
     * @param Labels 标签列表
     * @return 下拉树结构列表
     */
    public List<LabelTreeSelect> buildLabelTreeSelect(List<LabelData> Labels)
    {
        List<LabelData> LabelTrees = buildLabelTree(Labels);
        return LabelTrees.stream().map(LabelTreeSelect::new).collect(Collectors.toList());
    }

    /**
     * 构建树结构
     *
     * @param Labels 标签列表
     * @return 树结构列表
     */
    private List<LabelData> buildLabelTree(List<LabelData> Labels)
    {
        List<LabelData> returnList = new ArrayList<LabelData>();
        List<Long> tempList = new ArrayList<Long>();
        for (LabelData Label : Labels)
        {
            tempList.add(Label.getId());
        }
        for (LabelData Label : Labels)
        {
            // 如果是顶级节点, 遍历该父节点的所有子节点
            if (!tempList.contains(Label.getParentId()))
            {
                recursionFn(Labels, Label);
                returnList.add(Label);
            }
        }
        if (returnList.isEmpty())
        {
            returnList = Labels;
        }
        return returnList;
    }

    /**
     * 递归列表
     */
    private void recursionFn(List<LabelData> list, LabelData t)
    {
        // 得到子节点列表
        List<LabelData> childList = getChildList(list, t);
        t.setChildren(childList);
        for (LabelData tChild : childList)
        {
            if (hasChild(list, tChild))
            {
                recursionFn(list, tChild);
            }
        }
    }

    /**
     * 得到子节点列表
     */
    private List<LabelData> getChildList(List<LabelData> list, LabelData t)
    {
        List<LabelData> tlist = new ArrayList<LabelData>();
        Iterator<LabelData> it = list.iterator();
        while (it.hasNext())
        {
            LabelData n = (LabelData) it.next();
            if (StringUtils.isNotNull(n.getParentId()) && n.getParentId().compareTo(t.getId()) == 0)
            {
                n.setParentName(t.getLabelName());
                tlist.add(n);
            }
        }
        return tlist;
    }

    /**
     * 判断是否有子节点
     */
    private boolean hasChild(List<LabelData> list, LabelData t)
    {
        return getChildList(list, t).size() > 0 ? true : false;
    }


    /**
     * 初始化数据
     * @return
     */
    public List<LabelData> dataInfoList() {
        List<LabelData> dataList = new ArrayList<>();
        LabelData data = new LabelData();
        data.setId(1L);
        data.setLabelCode("01");
        data.setLabelName("最高级标签");
        data.setLabelValue("最高级标签值");
        data.setLabelDesc("最高级标签描述");
        dataList.add(data);

        LabelData childData = new LabelData();
        childData.setId(11L);
        childData.setParentId(data.getId());
        childData.setLabelCode("011");
        childData.setLabelName("子级标签");
        childData.setLabelValue("子级标签值");
        childData.setLabelDesc("子级标签描述");
        dataList.add(childData);
        return dataList;
    }
    
}
