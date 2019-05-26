package com.nine96kibs.nine96kibsserver.service.impl;

import com.nine96kibs.nine96kibsserver.dao.AccountMapper;
import com.nine96kibs.nine96kibsserver.dto.CommonResult;
import com.nine96kibs.nine96kibsserver.po.AccountModel;
import com.nine96kibs.nine96kibsserver.service.AccountService;
import com.nine96kibs.nine96kibsserver.vo.AccountInfoVO;
import com.nine96kibs.nine96kibsserver.vo.AccountVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AccountServiceImpl implements AccountService {

    @Autowired
    private AccountMapper accountMapper;

    @Override
    @Transactional
    public AccountInfoVO login(AccountVO account) {
        AccountModel user = accountMapper.selectAccountByUserName(account.getUsername());
        if (user != null && user.getPassword().equals(account.getPassword())){
            return new AccountInfoVO(user.getId(), user.getUsername(), user.getPassword());
        }
        return null;
    }

    @Override
    @Transactional
    public CommonResult registerAccount(AccountVO account) {
        if (accountMapper.selectAccountByUserName(account.getUsername()) == null){
            try {
                accountMapper.createNewAccount(account.getUsername(), account.getPassword());
                return new CommonResult().success();
            }catch (Exception e){
                return new CommonResult().failed("SOMETHING UNEXPECTED HAPPENED");
            }
        }
        return new CommonResult().failed("ACCOUNT EXIST");
    }
}
