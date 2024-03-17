package com.xxxx.dao.impl;

import com.xxxx.dao.IAccountDao;
import com.xxxx.po.Account;
import org.apache.commons.lang3.StringUtils;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * 账户模块接口的实现类
 */
@Repository
public class AccountDaoImpl implements IAccountDao {

    // 注入JdbcTemplate模板类
    @Resource
    private JdbcTemplate jdbcTemplate;

    /**
     * 添加账户记录，返回受影响的行数
     * @param account
     * @return
     */
    @Override
    public int addAccount(Account account) {
        // 定义sql语句
        String sql = "insert into tb_account (account_name,account_type,money,remark," +
                " create_time,update_time,user_id) values (?,?,?,?,now(),now(),?)";
        // 设置参数
        Object[] objs = {account.getAccountName(),account.getAccountType(),account.getMoney(),
                account.getRemark(),account.getUserId()};

        int row = jdbcTemplate.update(sql,objs);

        return row;
    }

    /**
     * 添加账户记录，返回主键
     * @param account
     * @return
     */
    @Override
    public int addAccountHasKey(Account account) {

        // 定义sql语句
        String sql = "insert into tb_account (account_name,account_type,money,remark," +
                " create_time,update_time,user_id) values (?,?,?,?,now(),now(),?)";

        // 定义KeyHolder对象  获取记录的主键值
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            // 预编译sql语句，并设置返回主键
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            // 设置参数
            ps.setString(1,account.getAccountName());
            ps.setString(2,account.getAccountType());
            ps.setDouble(3,account.getMoney());
            ps.setString(4,account.getRemark());
            ps.setInt(5,account.getUserId());
            // 返回预编译对象
            return ps;
        }, keyHolder);

        // 得到返回的主键
        int key = keyHolder.getKey().intValue();
        return key;
    }


    /**
     * 批量添加账户记录，返回受影响的行数
     * @param accounts
     * @return
     */
    @Override
    public int addAccountBatch(List<Account> accounts) {
        // 定义sql语句
        String sql = "insert into tb_account (account_name,account_type,money,remark," +
                " create_time,update_time,user_id) values (?,?,?,?,now(),now(),?)";

        int rows = jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                Account account = accounts.get(i);
                // 设置参数
                ps.setString(1,account.getAccountName());
                ps.setString(2,account.getAccountType());
                ps.setDouble(3,account.getMoney());
                ps.setString(4,account.getRemark());
                ps.setInt(5,account.getUserId());
            }

            @Override
            public int getBatchSize() {
                return accounts.size();
            }
        }).length;

        return rows;
    }


    /**
     * 查询指定用户的账户总记录数，返回总数量
     * @param userId
     * @return
     */
    @Override
    public int queryAccountCount(int userId) {
        // 定义sql语句
        String sql = "select count(1) from tb_account where user_id = ? ";
        // 查询方法
        int count = jdbcTemplate.queryForObject(sql, Integer.class, userId);
        return count;
    }


    /**
     * 查询指定账户记录详情，返回账户对象
     * @param accountId
     * @return
     */
    @Override
    public Account queryAccountById(int accountId) {
        // 定义sql语句
        String sql = "select * from tb_account where account_id = ? ";
        // 查询对象
        Account account = jdbcTemplate.queryForObject(sql, (ResultSet rs, int i) ->  {
            Account acc = new Account();
            acc.setAccountId(accountId);
            acc.setAccountName(rs.getString("account_name"));
            acc.setAccountType(rs.getString("account_type"));
            acc.setMoney(rs.getDouble("money"));
            acc.setRemark(rs.getString("remark"));
            acc.setUserId(rs.getInt("user_id"));
            acc.setCreateTime(rs.getDate("create_time"));
            acc.setUpdateTime(rs.getDate("update_time"));
            return acc;
        },accountId);
        return account;
    }

    /**
     * 多条件查询指定用户的账户记录列表，返回账户集合
     * @param userId    指定用户的ID
     * @param accountName   账户名称（模糊查询）
     * @param accountType   账户类型
     * @param createTime    创建时间（大于当前时间）
     * @return
     */
    @Override
    public List<Account> queryAccountByParams(Integer userId, String accountName, String accountType, String createTime) {
        // 定义sql语句
        String sql = "select * from tb_account where user_id = ? ";
        // 定义参数列表
        List<Object> params = new ArrayList<>();
        params.add(userId);

        // 判断参数是否为空，如果不为空，拼接sql语句及设置对应的参数
        // 账户名称
        if (StringUtils.isNotBlank(accountName)){
            // 拼接sql语句
            sql += " and account_name like concat('%',?,'%') ";
            // 设置参数
            params.add(accountName);
        }
        // 账户类型
        if (StringUtils.isNotBlank(accountType)){
            // 拼接sql语句
            sql += " and account_type = ? ";
            // 设置参数
            params.add(accountType);
        }
        // 创建时间
        if (StringUtils.isNotBlank(createTime)){
            // 拼接sql语句
            sql += " and create_time < ? ";
            // 设置参数
            params.add(createTime);
        }

        // 将集合转换为数组
        Object[] objs = params.toArray();
        // 查询集合
        List<Account> accountList = jdbcTemplate.query(sql, objs, (ResultSet rs, int i) ->  {
            Account acc = new Account();
            acc.setAccountId(rs.getInt("account_id"));
            acc.setAccountName(rs.getString("account_name"));
            acc.setAccountType(rs.getString("account_type"));
            acc.setMoney(rs.getDouble("money"));
            acc.setRemark(rs.getString("remark"));
            acc.setUserId(rs.getInt("user_id"));
            acc.setCreateTime(rs.getTimestamp("create_time"));
            acc.setUpdateTime(rs.getTimestamp("update_time"));
            return acc;
        });
        return accountList;
    }

    /**
     * 修改账户，返回受影响的行数
     * @param account
     * @return
     */
    @Override
    public int updateAccount(Account account) {
        // 定义sql
        String sql = "update tb_account set account_name = ? , account_type = ? , money = ? , remark = ? , " +
                " update_time = now(), user_id = ? where account_id = ?";
        // 设置参数
        Object[] objs = {account.getAccountName(),account.getAccountType(),account.getMoney(),
                        account.getRemark(),account.getUserId(),account.getAccountId()};
        int row = jdbcTemplate.update(sql, objs);
        return row;
    }

    /**
     * 批量修改账户记录，返回受影响的行数
     * @param accounts
     * @return
     */
    @Override
    public int updateAccountBatch(List<Account> accounts) {
        // 定义sql语句
        String sql = "update tb_account set account_name = ? , account_type = ? , money = ? , remark = ? , " +
                " update_time = now(), user_id = ? where account_id = ?";
        int rows = jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                Account account = accounts.get(i);
                // 设置参数
                ps.setString(1,account.getAccountName());
                ps.setString(2,account.getAccountType());
                ps.setDouble(3,account.getMoney());
                ps.setString(4,account.getRemark());
                ps.setInt(5,account.getUserId());
                ps.setInt(6,account.getAccountId());
            }

            @Override
            public int getBatchSize() {
                return accounts.size();
            }
        }).length;
        return rows;
    }

    /**
     * 删除账户记录，返回受影响的行数
     * @param accountId
     * @return
     */
    @Override
    public int deleteAccount(int accountId) {
        // 定义SQL语句
        String sql = "delete from tb_account where account_id = ?";
        int row = jdbcTemplate.update(sql,accountId);
        return row;
    }


    /**
     * 批量删除账户记录，返回受影响的行数
     * @param ids
     * @return
     */
    @Override
    public int deleteAccountBatch(Integer[] ids) {
        // 定义SQL语句
        String sql = "delete from tb_account where account_id = ?";
        int rows = jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                ps.setInt(1, ids[i]);
            }

            @Override
            public int getBatchSize() {
                return ids.length;
            }
        }).length;
        return rows;
    }


    /**
     * 支出
     * @param accountId
     * @param money
     * @return
     */
    @Override
    public int outAccount(Integer accountId, Double money) {
        String sql = "update tb_account set money = money - ? where account_id = ? ";
        Object[] objs = {money, accountId};
        return jdbcTemplate.update(sql,objs);
    }

    /**
     * 收入
     * @param accountId
     * @param money
     * @return
     */
    @Override
    public int inAccount(Integer accountId, Double money) {
        String sql = "update tb_account set money = money + ? where account_id = ? ";
        Object[] objs = {money, accountId};
        return jdbcTemplate.update(sql,objs);
    }

}
