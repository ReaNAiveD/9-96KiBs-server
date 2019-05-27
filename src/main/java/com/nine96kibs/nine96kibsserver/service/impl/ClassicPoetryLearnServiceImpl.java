package com.nine96kibs.nine96kibsserver.service.impl;

import com.nine96kibs.nine96kibsserver.dao.ClassicPoetryLearnMapper;
import com.nine96kibs.nine96kibsserver.po.ReciteLearnInfo;
import com.nine96kibs.nine96kibsserver.po.ReciteLearnProgress;
import com.nine96kibs.nine96kibsserver.po.ReciteToLearn;
import com.nine96kibs.nine96kibsserver.service.ClassicPoetryLearnService;
import com.nine96kibs.nine96kibsserver.vo.ReciteLearnChoice;
import com.nine96kibs.nine96kibsserver.vo.TaskInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

@Service
public class ClassicPoetryLearnServiceImpl implements ClassicPoetryLearnService {

    @Autowired
    private ClassicPoetryLearnMapper classicPoetryLearnMapper;

    /**
     * 背诵词条过程中选择
     *
     * @param learnChoice 背诵时进行的选择
     */
    @Override
    public void reciteChoose(ReciteLearnChoice learnChoice) {
        ReciteLearnProgress learnProgress;
        ReciteLearnInfo learnInfo = classicPoetryLearnMapper.selectLearnProgressByUserAndRecite(learnChoice.getUserId(), learnChoice.getReciteId());
        if (learnInfo == null) {
            learnProgress = new ReciteLearnProgress(learnChoice.getReciteId(), learnChoice.getUserId(), 0, learnChoice.getChoice(), learnChoice.getChoice(), new Date());
            classicPoetryLearnMapper.insertReciteLearnInfo(learnProgress);
        }
        else if (learnChoice.getChoice() == 2) {
            learnProgress = new ReciteLearnProgress(learnChoice.getReciteId(), learnChoice.getUserId(), 1, learnInfo.getImpressionCount() + 1, 2, new Date());
            classicPoetryLearnMapper.updateReciteLearnInfo(learnProgress);
        }
        else {
            learnProgress = new ReciteLearnProgress(learnChoice.getReciteId(), learnChoice.getUserId(), learnInfo.getIfCommand(), learnInfo.getImpressionCount() + learnChoice.getChoice(), learnChoice.getChoice(), new Date());
            classicPoetryLearnMapper.updateReciteLearnInfo(learnProgress);
        }
    }

    /**
     * 取消一条收藏
     *
     * @param userId   用户id
     * @param recipeId 条目id
     */
    @Override
    public void uncollectRecite(int userId, int recipeId) {
        classicPoetryLearnMapper.deleteCollectionRecite(userId, recipeId);
    }

    /**
     * 得到一份任务的学习情况列表，这份列表将传给前端作为当前的学习列表
     *
     * @param userId 用户id
     * @param taskId 任务id
     * @return 学习列表
     */
    @Override
    @Transactional
    public ReciteToLearn getLearnListByTask(int userId, int taskId) {
        List<ReciteLearnInfo> learnInfoList = classicPoetryLearnMapper.selectLearnInfoByUserAndTask(userId, taskId);
        if (learnInfoList.size() == 0) return null;
        List<ReciteToLearn> reciteToLearns = new ArrayList<>();
        for (ReciteLearnInfo learnInfo :
             learnInfoList) {
            if (learnInfo == null || learnInfo.getIfCommand() == null || learnInfo.getIfCommand() == 0) reciteToLearns.add(new ReciteToLearn(learnInfo));
        }
        return Collections.max(reciteToLearns);
    }

    /**
     * 得到某一任务掌握的条目
     *
     * @param userId 用户id
     * @param taskId 任务id
     * @return 条目列表
     */
    @Override
    public List<ReciteLearnInfo> getCommandRecite(int userId, int taskId) {
        return classicPoetryLearnMapper.selectCommandReciteByUserAndTask(userId, taskId);
    }

    /**
     * 将一条条目改为未掌握
     *
     * @param userId   用户id
     * @param recipeId 条目id
     */
    @Override
    public void setUncommand(int userId, int recipeId) {
        classicPoetryLearnMapper.setUncommand(userId, recipeId);
    }

    /**
     * 收藏一个条目
     *
     * @param userId   用户id
     * @param recipeId 条目id
     */
    @Override
    public void collectRecite(int userId, int recipeId) {
        classicPoetryLearnMapper.insertCollectionRecite(userId, recipeId);
    }

    /**
     * 得到所有收藏条目
     *
     * @param userId 用户id
     * @return 条目信息列表
     */
    @Override
    public List<ReciteLearnInfo> getReciteCollection(int userId) {
        return classicPoetryLearnMapper.selectCollectionRecite(userId);
    }

    @Override
    public List<TaskInfo> getTaskInfo(int userId){
        return classicPoetryLearnMapper.selectTaskInfo(userId);
    }
}
