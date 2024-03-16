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
        Account account = new Account(5,"西野七濑","歌手",200.0,"奖金",1);
        int row  = iAccountDao.addAccount(account);
        System.out.println(row);
    }
    @Test
    public void testAddAccountHasKey(){
        // 准备要添加的数据
        Account account = new Account(7,"李四","招商银行",200.0,"兼职费",2);

        // 调用对象的添加方法，返回主键
        int key = iAccountDao.addAccountHaskey(account);
        System.out.println("添加账户返回的主键：" + key);
    }
}
