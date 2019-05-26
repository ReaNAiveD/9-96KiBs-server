package com.nine96kibs.nine96kibsserver;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.nine96kibs.nine96kibsserver.dao.AccountMapper;
import com.nine96kibs.nine96kibsserver.dao.ClassicPoetryLearnMapper;
import com.nine96kibs.nine96kibsserver.dto.CommonResult;
import com.nine96kibs.nine96kibsserver.po.ReciteLearnProgress;
import com.nine96kibs.nine96kibsserver.po.ReciteToLearn;
import com.nine96kibs.nine96kibsserver.service.AccountService;
import com.nine96kibs.nine96kibsserver.service.ClassicPoetryLearnService;
import com.nine96kibs.nine96kibsserver.vo.AccountInfoVO;
import com.nine96kibs.nine96kibsserver.vo.AccountVO;
import com.nine96kibs.nine96kibsserver.vo.ReciteLearnChoice;
import okhttp3.*;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

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
        ReciteToLearn reciteToLearn= classicPoetryLearnService.getLearnListByTask(userId, 1);
        classicPoetryLearnService.reciteChoose(new ReciteLearnChoice(reciteToLearn.getReciteId(), userId, 1));
        reciteToLearn = classicPoetryLearnService.getLearnListByTask(userId, 1);
        classicPoetryLearnService.reciteChoose(new ReciteLearnChoice(reciteToLearn.getReciteId(), userId, 0));
        reciteToLearn = classicPoetryLearnService.getLearnListByTask(userId, 1);
        Assert.assertEquals(0, reciteToLearn.getRecitePrior(), 0.001);
        classicPoetryLearnService.reciteChoose(new ReciteLearnChoice(reciteToLearn.getReciteId(), userId, 2));
        Assert.assertEquals(1, classicPoetryLearnService.getCommandRecite(userId, 1).size());
        int commandReciteId = classicPoetryLearnService.getCommandRecite(userId, 1).get(0).getReciteId();
        classicPoetryLearnService.setUncommand(userId, commandReciteId);
        Assert.assertEquals(0, classicPoetryLearnService.getCommandRecite(userId, 1).size());
        classicPoetryLearnService.collectRecite(userId, commandReciteId);
        Assert.assertEquals(1, classicPoetryLearnService.getReciteCollection(userId).size());
        classicPoetryLearnService.uncollectRecite(userId, commandReciteId);
        Assert.assertEquals(0, classicPoetryLearnService.getReciteCollection(userId).size());
    }

    @Test
    public void httpRequestAccountTest(){
        try {
            Gson gson = new Gson();
            AccountVO accountVO = new AccountVO("realTest", "123");
            OkHttpClient httpClient = new OkHttpClient.Builder()
                    .readTimeout(100, TimeUnit.SECONDS)
                    .writeTimeout(60, TimeUnit.SECONDS)
                    .connectTimeout(60, TimeUnit.SECONDS)
                    .build();
            RequestBody requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), gson.toJson(accountVO));
            Request request = new Request.Builder()
                    .url("http://47.100.97.17:8848/account/register").post(requestBody).build();
            Response response = httpClient.newCall(request).execute();
            System.out.println(response.message());
            requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), gson.toJson(accountVO));
            request = new Request.Builder()
                    .url("http://47.100.97.17:8848/account/login").post(requestBody).build();
            response = httpClient.newCall(request).execute();
            Assert.assertTrue(response.isSuccessful());
            Assert.assertNotNull(response.body());
            JsonObject jsonObject = gson.fromJson(response.body().string(), JsonObject.class);
            AccountInfoVO accountInfoVO = gson.fromJson(jsonObject.get("data"), AccountInfoVO.class);
            Assert.assertEquals("realTest", accountInfoVO.getUsername());
            int userId = accountInfoVO.getId();
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    @Test
    public void httpRequestClassicPoetryTest(){
        try {
            Gson gson = new Gson();
            AccountVO accountVO = new AccountVO("realTest", "123");
            OkHttpClient httpClient = new OkHttpClient.Builder()
                    .readTimeout(10, TimeUnit.SECONDS)
                    .writeTimeout(6, TimeUnit.SECONDS)
                    .connectTimeout(6, TimeUnit.SECONDS)
                    .build();
            RequestBody requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), gson.toJson(accountVO));
            Request request = new Request.Builder()
                    .url("http://47.100.97.17:8848/account/login").post(requestBody).build();
            Response response = httpClient.newCall(request).execute();
            Assert.assertTrue(response.isSuccessful());
            Assert.assertNotNull(response.body());
            CommonResult commonResult = gson.fromJson(response.body().string(), CommonResult.class);
            AccountInfoVO accountInfoVO = gson.fromJson(gson.toJson(commonResult.getData()), AccountInfoVO.class);
            //AccountInfoVO accountInfoVO = gson.fromJson(jsonObject.get("data"), AccountInfoVO.class);
            Assert.assertEquals("realTest", accountInfoVO.getUsername());
            int userId = accountInfoVO.getId();

            request = new Request.Builder()
                    .url("http://47.100.97.17:8848/classic-poetry/normal/learn-list?user-id=" + userId + "&task-id=1").get().build();
            response = httpClient.newCall(request).execute();
            Assert.assertTrue(response.isSuccessful());
            Assert.assertNotNull(response.body());
            System.out.println(response.body().toString());
            commonResult = gson.fromJson(response.body().string(), CommonResult.class);
            ReciteToLearn reciteToLearn = gson.fromJson(gson.toJson(commonResult.getData()), ReciteToLearn.class);
        } catch (IOException e){
            e.printStackTrace();
        }
    }
}
