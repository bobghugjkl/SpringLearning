package com.xxxx.ssm.dao;


import com.xxxx.ssm.vo.Account;

public interface AccountDao {

    public Account selectById(Integer id);

    public int save(Account account);

    public int update(Account account);

    public int delete(Integer id);



}