package com.nine96kibs.nine96kibsserver.po;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ReciteLearnInfo {

    ReciteLearnInfo(ReciteLearnInfo learnInfo){
        if (learnInfo != null){
            this.reciteId = learnInfo.getReciteId();
            if (learnInfo.getLatestLearn() == null) {
                this.ifCommand = 0;
                this.latestLearn = null;
                this.impressionCount = 0;
                this.latestChoice = -1;
            }
            else {
                this.ifCommand = learnInfo.getIfCommand();
                this.latestLearn = learnInfo.getLatestLearn();
                this.impressionCount = learnInfo.getImpressionCount();
                this.latestChoice = learnInfo.getLatestChoice();
            }
            this.topic = learnInfo.getTopic();
            this.topicSub = learnInfo.getTopicSub();
            this.answer = learnInfo.getAnswer();
            this.answerSub = learnInfo.getAnswerSub();
            this.articleId = learnInfo.getArticleId();
        }
    }

    private Integer reciteId;

    private Integer ifCommand;

    private Date latestLearn;

    private Integer impressionCount;

    private Integer latestChoice;

    private String topic;

    private String topicSub;

    private String answer;

    private String answerSub;

    private Integer articleId;

}
