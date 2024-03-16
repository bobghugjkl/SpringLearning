package com.xxxx.Dao.Impl;

import com.xxxx.Dao.IAccountDao;
import com.xxxx.po.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;
@Repository
public class AccountDaoImpl implements IAccountDao {
    @Resource
    private JdbcTemplate jdbcTemplate;
    public int addAccount(Account account) {
        String sql = "insert into tb_account(account_id,account_name,account_type,money,remark," +
                "user_id,create_time,update_time) values (?,?,?,?,?,?,now(),now())";
        Object[] objs = {account.getAccountId(),account.getAccountName(),account.getAccountType(),
                account.getMoney(),account.getRemark(),account.getUserId()};
        return jdbcTemplate.update(sql,objs);

    }

    public int addAccountHaskey(final Account account) {
        String sql = "insert into tb_account(account_id,account_name,account_type,money,remark," +
                "user_id,create_time,update_time) values (?,?,?,?,?,?,now(),now())";;
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1,account.getAccountId());
            ps.setString(2,account.getAccountName());
            ps.setString(3,account.getAccountType());
            ps.setDouble(4,account.getMoney());
            ps.setString(5,account.getRemark());
            ps.setInt(6,account.getUserId());
            return ps;
        },keyHolder);
        int key = keyHolder.getKey().intValue();
        return key;
    }

    public int addAccountBatch(List<Account> accounts) {
        return 0;
    }

    public int queryAccountCount(int userId) {
        return 0;
    }

    public Account queryAccountById(int userId) {
        return null;
    }

    public List<Account> queryAccountByParams(Integer userId, String accountName, String accountType, String createTime) {
        return null;
    }

    public int updateAccount(Account account) {
        return 0;
    }

    public int updateAccountBatch(List<Account> accounts) {
        return 0;
    }

    public int deleteAccount(int accountId) {
        return 0;
    }

    public int deleteAccountBatch(Integer[] ids) {
        return 0;
    }
}
