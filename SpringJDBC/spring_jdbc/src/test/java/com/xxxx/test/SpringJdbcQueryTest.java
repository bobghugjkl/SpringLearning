package com.xxxx.test;

import com.xxxx.dao.IAccountDao;
import com.xxxx.po.Account;
import org.junit.Test;

import javax.annotation.Resource;
import java.util.List;

public class SpringJdbcQueryTest extends BaseTest {

    @Resource
    private IAccountDao accountDao;

    /**
     * 查询指定用户的账户总记录数，返回总数量
     */
    @Test
    public void testQueryAccountCount(){
        int total = accountDao.queryAccountCount(1);

        System.out.println("查询指定用户的账户总记录数：" + total);
    }

    /**
     * 查询指定账户记录的详情，返回账户对象
     */
    @Test
    public void testQueryAccountById(){
        Account account = accountDao.queryAccountById(1);
        System.out.println("账户详情：" + account.toString());
    }

    /**
     * 多条件查询，返回账户列表
     */
    @Test
    public void testQueryAccountList(){
        List<Account> accountList = accountDao.queryAccountByParams(3,null,null,null);
        // System.out.println(accountList.toString());

        List<Account> accountList2 = accountDao.queryAccountByParams(3,"5",null,null);
        //System.out.println(accountList2.toString());

        List<Account> accountList3 = accountDao.queryAccountByParams(3,"5","中国银行",null);
        System.out.println(accountList3.toString());
    }

}
