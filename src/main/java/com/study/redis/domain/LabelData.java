package com.study.redis.domain;


import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
public class LabelData implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    private Long parentId;

    private String labelCode;

    private String labelName;

    private String labelValue;

    private String labelDesc;

    private String parentName;


    /** 子标签 */
    private List<LabelData> children = new ArrayList<LabelData>();

}
