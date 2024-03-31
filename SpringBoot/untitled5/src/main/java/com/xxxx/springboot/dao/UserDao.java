package com.xxxx.springboot.dao;

import com.xxxx.springboot.query.UserQuery;
import com.xxxx.springboot.vo.User;

import java.util.List;

public interface UserDao {
    public User queryUserByUserName(String userName);
    public User queryById(Integer id);

    public int save(User user);

    public int update(User user);

    public List<User> selectByParams(UserQuery userQuery);
    public int delete(Integer id);

}
