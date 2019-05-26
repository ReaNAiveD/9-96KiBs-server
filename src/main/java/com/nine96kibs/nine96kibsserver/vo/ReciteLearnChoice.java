package com.nine96kibs.nine96kibsserver.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ReciteLearnChoice {

    private Integer reciteId;

    private Integer userId;

    /**
     * 0 - 不认识
     * 1 - 有印象
     * 2 - 已掌握
     */
    private Integer choice;

}
