package com.nine96kibs.nine96kibsserver;

import com.nine96kibs.nine96kibsserver.dao.AccountMapper;
import com.nine96kibs.nine96kibsserver.dao.ClassicPoetryLearnMapper;
import com.nine96kibs.nine96kibsserver.dto.CommonResult;
import com.nine96kibs.nine96kibsserver.po.ClassicPoetryReciteModel;
import com.nine96kibs.nine96kibsserver.po.ReciteLearnInfo;
import com.nine96kibs.nine96kibsserver.po.ReciteLearnProgress;
import com.nine96kibs.nine96kibsserver.po.ReciteToLearn;
import com.nine96kibs.nine96kibsserver.service.AccountService;
import com.nine96kibs.nine96kibsserver.service.ClassicPoetryLearnService;
import com.nine96kibs.nine96kibsserver.vo.AccountVO;
import com.nine96kibs.nine96kibsserver.vo.ReciteLearnChoice;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ApplicationTests {

    @Autowired
    private AccountMapper accountMapper;

    @Autowired
    private AccountService accountService;

    @Autowired
    private ClassicPoetryLearnMapper classicPoetryLearnMapper;

    @Autowired
    private ClassicPoetryLearnService classicPoetryLearnService;

    @Test
    @Rollback
    @Transactional
    public void accountMapperTest(){
        accountMapper.createNewAccount("test", "123");
        Assert.assertEquals("123", accountMapper.selectAccountByUserName("test").getPassword());
        Assert.assertNull(accountMapper.selectAccountByUserName("shouldnotexist"));
    }

    @Test
    @Rollback
    @Transactional
    public void accountServiceTest(){
        Assert.assertEquals(200, accountService.registerAccount(new AccountVO("test", "123")).getCode());
        Assert.assertEquals(500, accountService.registerAccount(new AccountVO("test", "123")).getCode());
        Assert.assertNotNull(accountService.login(new AccountVO("test", "123")));
        Assert.assertNull(accountService.login(new AccountVO("test", "12")));
    }

    @Test
    @Transactional
    @Rollback
    public void classicPoetryLearnMapperTest(){
        accountMapper.createNewAccount("test", "123");
        int userId = accountMapper.selectAccountByUserName("test").getId();
        Assert.assertNull(classicPoetryLearnMapper.selectLearnProgressByUserAndRecite(userId, 1));
        classicPoetryLearnMapper.insertReciteLearnInfo(new ReciteLearnProgress(1, userId, 0, 0, -1, new Date()));
        classicPoetryLearnMapper.insertReciteLearnInfo(new ReciteLearnProgress(2, userId, 1, 1, -1, new Date()));
        Assert.assertNotNull(classicPoetryLearnMapper.selectLearnProgressByUserAndRecite(userId, 1));
        Assert.assertEquals(2, classicPoetryLearnMapper.selectLearnInfoByUserAndTask(userId, 1).size());
        Assert.assertEquals(1, classicPoetryLearnMapper.selectCommandReciteByUserAndTask(userId, 1).size());
        classicPoetryLearnMapper.insertCollectionRecite(userId, 1);
        Assert.assertEquals(1, classicPoetryLearnMapper.selectCollectionRecite(userId).size());
        classicPoetryLearnMapper.deleteCollectionRecite(userId, 1);
        Assert.assertEquals(0, classicPoetryLearnMapper.selectCollectionRecite(userId).size());
        classicPoetryLearnMapper.setUncommand(userId, 2);
        Assert.assertEquals(0, classicPoetryLearnMapper.selectCommandReciteByUserAndTask(userId, 1).size());
    }

    @Test
    @Transactional
    @Rollback
    public void classicPoetryLearnServiceTest(){
        accountMapper.createNewAccount("test", "123");
        int userId = accountMapper.selectAccountByUserName("test").getId();
        List<ReciteToLearn> learnInfos = classicPoetryLearnService.getLearnListByTask(userId, 1);
        Assert.assertEquals(2, learnInfos.size());
        ReciteToLearn reciteToLearn0 = learnInfos.get(0);
        classicPoetryLearnService.reciteChoose(new ReciteLearnChoice(reciteToLearn0.getReciteId(), userId, 1));
        ReciteToLearn reciteToLearn1 = learnInfos.get(1);
        classicPoetryLearnService.reciteChoose(new ReciteLearnChoice(reciteToLearn1.getReciteId(), userId, 0));
        List<ReciteToLearn> learnInfos1 = classicPoetryLearnService.getLearnListByTask(userId, 1);
        Assert.assertEquals(2, learnInfos1.size());
        ReciteToLearn reciteToLearn10 = learnInfos1.get(0);
        System.out.println("reciteToLearn10.getLatestChoice() : " + reciteToLearn10.getLatestChoice());
        System.out.println("learnInfos1.get(1).getLatestChoice() : " + learnInfos1.get(1).getLatestChoice());
        Assert.assertEquals(0, reciteToLearn10.getRecitePrior(), 0.001);
        classicPoetryLearnService.reciteChoose(new ReciteLearnChoice(reciteToLearn10.getReciteId(), userId, 2));
        Assert.assertEquals(1, classicPoetryLearnService.getCommandRecite(userId, 1).size());
        int commandReciteId = classicPoetryLearnService.getCommandRecite(userId, 1).get(0).getReciteId();
        classicPoetryLearnService.setUncommand(userId, commandReciteId);
        Assert.assertEquals(0, classicPoetryLearnService.getCommandRecite(userId, 1).size());
        classicPoetryLearnService.collectRecite(userId, commandReciteId);
        Assert.assertEquals(1, classicPoetryLearnService.getReciteCollection(userId).size());
        classicPoetryLearnService.uncollectRecite(userId, commandReciteId);
        Assert.assertEquals(0, classicPoetryLearnService.getReciteCollection(userId).size());
    }
}
