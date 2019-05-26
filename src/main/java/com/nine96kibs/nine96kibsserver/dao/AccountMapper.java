package com.nine96kibs.nine96kibsserver.dao;

import com.nine96kibs.nine96kibsserver.po.AccountModel;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface AccountMapper {

    public int createNewAccount(@Param("username") String username, @Param("password") String password);

    public AccountModel selectAccountByUserName(@Param("username") String username);
}
