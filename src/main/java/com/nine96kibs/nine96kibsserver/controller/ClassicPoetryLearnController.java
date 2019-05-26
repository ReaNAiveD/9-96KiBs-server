package com.nine96kibs.nine96kibsserver.controller;

import com.nine96kibs.nine96kibsserver.dto.CommonResult;
import com.nine96kibs.nine96kibsserver.po.ReciteLearnInfo;
import com.nine96kibs.nine96kibsserver.po.ReciteToLearn;
import com.nine96kibs.nine96kibsserver.service.ClassicPoetryLearnService;
import com.nine96kibs.nine96kibsserver.vo.ReciteLearnChoice;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/classic-poetry/normal")
public class ClassicPoetryLearnController {

    @Autowired
    private ClassicPoetryLearnService classicPoetryLearnService;

    @PostMapping("/choose")
    public CommonResult chooseFamiliarity(@RequestBody ReciteLearnChoice learnChoice){
        try {
                classicPoetryLearnService.reciteChoose(learnChoice);
        } catch (Exception e){
            e.printStackTrace();
            return new CommonResult().failed("记录选择时出现问题");
        }
        return new CommonResult().success();
    }

    @GetMapping("/learn-list")
    public CommonResult getLearnList(@RequestParam("user-id") int userId, @RequestParam("task-id") int taskId){
        ReciteToLearn learn = classicPoetryLearnService.getLearnListByTask(userId, taskId);
        return new CommonResult().success(learn);
    }

    @GetMapping("/command-list")
    public CommonResult getCommandList(@RequestParam("user-id") int userId, @RequestParam("task-id") int taskId){
        List<ReciteLearnInfo> learnInfos = classicPoetryLearnService.getCommandRecite(userId, taskId);
        return new CommonResult().success(learnInfos);
    }

    @PostMapping("/uncommand")
    public CommonResult uncommand(@RequestParam("user-id") int userId, @RequestParam("recite-id") int taskId){
        classicPoetryLearnService.setUncommand(userId, taskId);
        return new CommonResult().success();
    }

    @PostMapping("/collect")
    public CommonResult collect(@RequestParam("user-id") int userId, @RequestParam("recite-id") int reciteId){
        classicPoetryLearnService.collectRecite(userId, reciteId);
        return new CommonResult().success();
    }

    @GetMapping("/collection")
    public CommonResult getCollection(@RequestParam("user-id") int userId){
        List<ReciteLearnInfo> learnInfos = classicPoetryLearnService.getReciteCollection(userId);
        return new CommonResult().success(learnInfos);
    }

    @PostMapping("/uncollection")
    public CommonResult uncollect(@RequestParam("user-id") int userId, @RequestParam("recite-id") int reciteId){
        classicPoetryLearnService.uncollectRecite(userId, reciteId);
        return new CommonResult().success();
    }

}
