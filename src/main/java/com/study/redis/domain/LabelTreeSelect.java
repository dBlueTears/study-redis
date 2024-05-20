package com.study.redis.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class LabelTreeSelect implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    private Long parentId;

    private String labelName;

    private String labelDesc;


    /** 子标签 */
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private List<LabelTreeSelect> children = new ArrayList();

    public LabelTreeSelect()
    {

    }

    public LabelTreeSelect(LabelData data)
    {
        this.id = data.getId();
        this.parentId = data.getParentId();
        this.labelName = data.getLabelName();
        this.labelDesc = data.getLabelDesc();
        this.children = data.getChildren().stream().map(LabelTreeSelect::new).collect(Collectors.toList());
    }

}
