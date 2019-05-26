package com.nine96kibs.nine96kibsserver.controller;

import com.nine96kibs.nine96kibsserver.config.InterceptorConfiguration;
import com.nine96kibs.nine96kibsserver.dto.CommonResult;
import com.nine96kibs.nine96kibsserver.service.AccountService;
import com.nine96kibs.nine96kibsserver.vo.AccountInfoVO;
import com.nine96kibs.nine96kibsserver.vo.AccountVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/account")
public class AccountController {

    @Autowired
    private AccountService accountService;

    @PostMapping("/register")
    @ResponseBody
    public Object register(@RequestBody AccountVO account){
        return accountService.registerAccount(account);
    }

    @PostMapping("/login")
    @ResponseBody
    public CommonResult login(@RequestBody AccountVO account, HttpSession session){
        AccountInfoVO user = accountService.login(account);
        if (user == null){
            return new CommonResult().failed("INCORRECT USERNAME OR PASSWORD");
        }
        session.setAttribute(InterceptorConfiguration.SESSION_KEY, account);
        return new CommonResult().success(user);
    }

    @PostMapping("/logout")
    @ResponseBody
    public CommonResult logout(HttpSession session){
        session.removeAttribute(InterceptorConfiguration.SESSION_KEY);
        return new CommonResult().success();
    }

}
