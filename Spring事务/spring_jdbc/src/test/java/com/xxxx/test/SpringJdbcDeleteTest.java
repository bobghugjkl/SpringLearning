package com.xxxx.test;

import com.xxxx.dao.IAccountDao;
import org.junit.Test;

import javax.annotation.Resource;

public class SpringJdbcDeleteTest extends BaseTest {

    @Resource
    private IAccountDao accountDao;

    /**
     * 删除账户记录，返回受影响的行数
     */
    @Test
    public void testDeleteAccount(){
        int row = accountDao.deleteAccount(1);
        System.out.println("删除账户记录： " + row);
    }

    /**
     * 批量删除账户记录，返回受影响的行数
     */
    @Test
    public void testDeleteBatch(){
        Integer[] ids = {2,3,4};
        int rows = accountDao.deleteAccountBatch(ids);
        System.out.println("批量删除：" + rows);
    }
}
