package com.xxxx.test;

import com.xxxx.dao.IAccountDao;
import com.xxxx.po.Account;
import org.junit.Test;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * 账户模块添加操作测试类
 */
public class SpringJdbcAddTest extends BaseTest {

    // 注入
    @Resource
    private IAccountDao accountDao;

    /**
     * 添加账户记录，返回受影响的行数
     */
    @Test
    public void testAddAccount(){
        // 准备要添加的数据
        Account account = new Account("账户3","工商银行",200.0,"奖金",1);
        // 调用对象中的添加方法，返回受影响的行数
        int row = accountDao.addAccount(account);

        System.out.println("添加账户，受影响的行数：" + row);
    }

    /**
     * 添加账户记录，返回主键
     */
    @Test
    public void testAddAccountHasKey(){
        // 准备要添加的数据
        Account account = new Account("账户4","中国银行",300.0,"绩效奖",2);
        // 调用对象中的添加方法，返回主键
        int key = accountDao.addAccountHasKey(account);

        System.out.println("添加账户，返回主键：" + key);
    }

    /**
     * 批量添加账户记录，返回受影响的行数
     */
    @Test
    public void testBatchAddAccount(){

        // 准备要添加的数据
        Account account = new Account("账户5","农业银行",700.0,"奖金",3);
        Account account2 = new Account("账户6","工商银行",890.0,"早餐",3);
        Account account3 = new Account("账户7","中国银行",560.0,"绩效奖",3);

        List<Account> accounts = new ArrayList<>();
        accounts.add(account);
        accounts.add(account2);
        accounts.add(account3);

        int rows = accountDao.addAccountBatch(accounts);

        System.out.println("批量添加账户记录，返回受影响的行数：" + rows);

    }




}
