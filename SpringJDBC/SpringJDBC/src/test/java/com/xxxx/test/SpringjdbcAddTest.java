package com.xxxx.test;

import com.xxxx.Dao.IAccountDao;
import com.xxxx.po.Account;
import org.junit.Test;

import javax.annotation.Resource;

public class SpringjdbcAddTest extends BaseTest{
    @Resource
    private IAccountDao iAccountDao;

    @Test
    public void testAddAccount(){
        Account account = new Account(3,"西野七濑","歌手",200.0,"奖金",1);
        int row  = iAccountDao.addAccount(account);
        System.out.println(row);
    }
}
