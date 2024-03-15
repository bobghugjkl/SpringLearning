package com.xxxx.Dao;

import com.xxxx.po.Account;

import java.util.List;

public interface IAccountDao {
    public int addAccount(Account account);
    public int addAccountHaskey(Account account);
    public int addAccountBatch(List<Account>accounts);
    public int queryAccountCount(int userId);

    public Account queryAccountById(int userId);
    public List<Account> queryAccountByParams(Integer userId,String accountName,String accountType,String createTime);
    public int updateAccount(Account account);
    public int updateAccountBatch(List<Account>accounts);
    public int deleteAccount(int accountId);
    public int deleteAccountBatch(Integer[] ids);
}
