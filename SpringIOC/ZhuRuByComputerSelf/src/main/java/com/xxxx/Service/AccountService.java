package com.xxxx.Service;

import com.xxxx.Dao.AccountDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

public class AccountService {
    @Autowired
    @Qualifier(value = "accountDao")
    private AccountDao accountDao;
    public void test(){
        System.out.println("AccountService running");
    }
}
