package com.xxxx.test;

import com.xxxx.dao.IAccountDao;
import com.xxxx.po.Account;
import org.junit.Test;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

public class SpringJdbcUpdateTest extends BaseTest {

    @Resource
    private IAccountDao accountDao;

    /**
     * 修改账户记录，返回受影响的行数
     */
    @Test
    public void testUpdateAccount(){
        Account account = new Account("账户11","农业银行",2000.0,"奖金",1);
        account.setAccountId(1);
        int row = accountDao.updateAccount(account);

        System.out.println("修改账户记录，返回受影响的行数：" + row);
    }

    /**
     * 批量修改账户记录，返回受影响的行数
     */
    @Test
    public void testBatchUpdateAccount(){
        Account account = new Account("账户55","农业银行",7700.0,"奖金",3);
        account.setAccountId(5);
        Account account2 = new Account("账户66","工商银行",8890.0,"早餐",3);
        account2.setAccountId(6);
        Account account3 = new Account("账户77","中国银行",5560.0,"绩效奖",3);
        account3.setAccountId(7);

        List<Account> accounts = new ArrayList<>();
        accounts.add(account);
        accounts.add(account2);
        accounts.add(account3);

        int rows = accountDao.updateAccountBatch(accounts);

        System.out.println("批量更新账户记录，返回受影响的行数：" + rows);

    }
}
