package com.nine96kibs.nine96kibsserver.dao;

import com.nine96kibs.nine96kibsserver.po.ClassicPoetryReciteModel;
import com.nine96kibs.nine96kibsserver.po.ReciteLearnInfo;
import com.nine96kibs.nine96kibsserver.po.ReciteLearnProgress;
import com.nine96kibs.nine96kibsserver.vo.ReciteLearnChoice;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface ClassicPoetryLearnMapper {

    /**
     * 通过用户id和任务id选择该任务完成信息
     * @param userId 用户id
     * @param taskId 任务id
     * @return 词条学习信息列表，未学习大部分信息为null
     */
    List<ReciteLearnInfo> selectLearnInfoByUserAndTask(@Param("user_id")int userId, @Param("task_id")int taskId);

    /**
     * 选取用户收藏的词条
     * @param userId 用户id
     * @return 收藏的词条列表
     */
    List<ReciteLearnInfo> selectCollectionRecite(@Param("user_id")int userId);

    /**
     * 插入学习情况
     * @param learnProgress 更新的学习情况列表
     * @return 默认
     */
    int insertReciteLearnInfo(@Param("learn_progress")ReciteLearnProgress learnProgress);

    /**
     * 更新学习情况
     * @param learnProgress 更新的学习情况列表
     * @return 默认
     */
    int updateReciteLearnInfo(@Param("learn_progress")ReciteLearnProgress learnProgress);

    /**
     * 根据用户和词条id选择当前学习情况
     * @param userId 用户id
     * @param reciteId 词条id
     * @return 当前学习情况，未学习为null
     */
    ReciteLearnInfo selectLearnProgressByUserAndRecite(@Param("user_id")int userId, @Param("recite_id") int reciteId);

    /**
     * 根据用户和任务id选择用户已完成的该任务下词条列表
     * @param user_id 用户id
     * @param taskId 任务id
     * @return 已完成的词条列表
     */
    List<ReciteLearnInfo> selectCommandReciteByUserAndTask(@Param("user_id") int user_id, @Param("task_id") int taskId);

    /**
     * 将一词条设置为未掌握
     * @param userId 用户id
     * @param reciteId 词条id
     * @return 默认
     */
    int setUncommand(@Param("user_id") int userId, @Param("recite_id") int reciteId);

    /**
     * 插入用户收藏词条信息
     * @param userId 用户id
     * @param reciteId 词条id
     * @return 默认
     */
    int insertCollectionRecite(@Param("user_id") int userId, @Param("recite_id") int reciteId);

    /**
     * 删除一条收藏信息
     * @param usrId 用户id
     * @param reciteId 条目id
     * @return 默认
     */
    int deleteCollectionRecite(@Param("user_id") int usrId, @Param("recite_id") int reciteId);

}
