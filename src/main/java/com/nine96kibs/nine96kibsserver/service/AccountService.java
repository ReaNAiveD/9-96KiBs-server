package com.nine96kibs.nine96kibsserver.service;

import com.nine96kibs.nine96kibsserver.dto.CommonResult;
import com.nine96kibs.nine96kibsserver.vo.AccountInfoVO;
import com.nine96kibs.nine96kibsserver.vo.AccountVO;

public interface AccountService {

    public CommonResult registerAccount(AccountVO account);

    public AccountInfoVO login(AccountVO account);

}
