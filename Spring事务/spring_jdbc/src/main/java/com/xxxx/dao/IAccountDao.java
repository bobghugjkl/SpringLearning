package com.xxxx.dao;

import com.xxxx.po.Account;

import java.util.List;

/**
 * 账户模块 接口定义
 *      1. 添加账户
 *          添加账户记录，返回受影响的行数
 *          添加账户记录，返回主键
 *          批量添加账户记录，返回受影响的行数
 *      2. 修改账户
 *          修改账户记录，返回受影响的行数
 *          批量修改账户记录，返回受影响的行数
 *      3. 删除账户
 *          删除账户记录，返回受影响的行数
 *  *       批量删除账户记录，返回受影响的行数
 *      4. 查询账户
 *          查询指定账户的账户的总记录数，返回总记录数
 *          查询指定账户的账户详情，返回账户对象
 *          多条件查询指定用户的账户列表，返回账户集合
 */
public interface IAccountDao {

    /**
     * 添加账户
     *      添加账户记录，返回受影响的行数
     * @param account
     * @return
     */
    public int addAccount(Account account);

    /**
     * 添加账户
     *      添加账户记录，返回主键
     * @param account
     */
    public int addAccountHasKey(Account account);

    /**
     * 添加账户
     *      量添加账户记录，返回受影响的行数
     * @param accounts
     * @return
     */
    public int addAccountBatch(List<Account> accounts);

    /**
     * 查询账户
     *      查询指定用户的账户的总记录数，返回总记录数
     * @param userId
     * @return
     */
    public int queryAccountCount(int userId);

    /**
     * 查询账户
     *      查询指定账户的账户详情，返回账户对象
     * @param accountId
     * @return
     */
    public Account queryAccountById(int accountId);

    /**
     * 查询账户
     *      多条件查询指定用户的账户列表，返回账户集合
     * @param userId    指定用户的ID
     * @param accountName   账户名称（模糊查询）
     * @param accountType   账户类型
     * @param createTime    创建时间（大于当前时间）
     * @return
     */
    public List<Account> queryAccountByParams(Integer userId, String accountName,String accountType, String createTime);

    /**
     * 修改账户
     *      修改账户记录，返回受影响的行数
     * @param account
     * @return
     */
    public int updateAccount(Account account);

    /**
     * 修改账户
     *      批量修改账户记录，返回受影响的行数
     * @param accounts
     * @return
     */
    public int updateAccountBatch(List<Account> accounts);

    /**
     * 删除账户
     *      删除账户记录，返回受影响的行数
     * @param accountId
     * @return
     */
    public int deleteAccount(int accountId);

    /**
     * 删除账户
     *      批量删除账户记录，返回受影响的行数
     * @param ids
     * @return
     */
    public int deleteAccountBatch(Integer[] ids);

    /**
     * 支出
     * @param accountId
     * @param money
     * @return
     */
    public int outAccount(Integer accountId, Double money);

    /**
     * 收入
     * @param accountId
     * @param money
     * @return
     */
    public int inAccount(Integer accountId, Double money);

}
