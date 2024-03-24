package com.xxxx.ssm.service;

import com.xxxx.ssm.dao.AccountDao;
import com.xxxx.ssm.vo.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AccountService {

    @Autowired
    private AccountDao accountDao;

    public  Account selectById(Integer id){
        return accountDao.selectById(id);
    }

    public int saveAccount(Account account){
        return accountDao.save(account);
    }

    public int updateAccount(Account account){
        return accountDao.update(account);
    }

    public int delAccount(Integer id){
        return accountDao.delete(id);
    }


}
