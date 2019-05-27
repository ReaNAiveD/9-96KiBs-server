package com.nine96kibs.nine96kibsserver.po;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
public class ReciteLearnProgress {
    private Integer reciteId;

    private Integer userId;

    private Integer ifCommand;

    private Integer impressionCount;

    /**
     * -1 - 无
     * 0 - 不认识
     * 1 - 有印象
     * 2 - 已掌握
     */
    private Integer latestChoice;

    private Date latestLearn;
}
