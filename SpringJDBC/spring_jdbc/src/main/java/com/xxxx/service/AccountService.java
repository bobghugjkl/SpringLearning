package com.xxxx.service;

import com.xxxx.dao.IAccountDao;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

@Service
public class AccountService {

    @Resource
    private IAccountDao accountDao;

    /**
     * 转账业务操作
     * @param outId 支出账户
     * @param inId  收入账户
     * @param money 金额
     * @return  1=成功，0=失败
     */
    @Transactional(propagation = Propagation.REQUIRED)
    public int toupdateAccountByTranfer(Integer outId, Integer inId, Double money){
        int code = 0; // 成功或失败  1=成功，0=失败

        /**
         * 账户A向账户B转账100元
         *  账户A：金额-100
         *  账户B：金额+100
         */
        // 账户A 支出，修改账户金额，返回受影响的行数
        int outRow = accountDao.outAccount(outId,money);

        int i = 1/0;

        // 账户B 收入，修改账户金额，返回受影响的行数
        int inRow = accountDao.inAccount(inId, money);

        // 如果支出和收入两个操作都执行成功，表示转账成功
        if (outRow == 1 && inRow == 1) {
            code = 1; // 成功
        }

        return code;
    }

}
