package com.nine96kibs.nine96kibsserver.service;

import com.nine96kibs.nine96kibsserver.po.ReciteLearnInfo;
import com.nine96kibs.nine96kibsserver.po.ReciteToLearn;
import com.nine96kibs.nine96kibsserver.vo.ReciteLearnChoice;

import java.util.List;

public interface ClassicPoetryLearnService {

    /**
     * 背诵词条过程中选择
     * @param learnChoice 背诵时进行的选择
     */
    void reciteChoose(ReciteLearnChoice learnChoice);

    /**
     * 得到一份任务的下一个学习，这份列表将传给前端作为当前的学习列表
     * @param userId 用户id
     * @param taskId 任务id
     * @return 学习
     */
    ReciteToLearn getLearnListByTask(int userId, int taskId);

    /**
     * 得到某一任务掌握的条目
     * @param userId 用户id
     * @param taskId 任务id
     * @return 条目列表
     */
    List<ReciteLearnInfo> getCommandRecite(int userId, int taskId);

    /**
     * 将一条条目改为未掌握
     * @param userId 用户id
     * @param recipeId 条目id
     */
    void setUncommand(int userId, int recipeId);

    /**
     * 收藏一个条目
     * @param userId 用户id
     * @param recipeId 条目id
     */
    void collectRecite(int userId, int recipeId);

    /**
     * 得到所有收藏条目
     * @param userId 用户id
     * @return 条目信息列表
     */
    List<ReciteLearnInfo> getReciteCollection(int userId);

    /**
     * 取消一条收藏
     * @param userId 用户id
     * @param recipeId 条目id
     */
    void uncollectRecite(int userId, int recipeId);
}
