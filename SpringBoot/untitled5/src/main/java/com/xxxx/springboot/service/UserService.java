package com.xxxx.springboot.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xxxx.springboot.dao.UserDao;
import com.xxxx.springboot.query.UserQuery;
import com.xxxx.springboot.utils.AssertUtil;
import com.xxxx.springboot.vo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserDao userDao;
    public User queryUserByUserName(String userName){
        return userDao.queryUserByUserName(userName);
    }


    public User queryUserByUserId(Integer userId){
        return userDao.queryById(userId);
    }

    public void saveUser(User user) {
        AssertUtil.isTrue(StringUtils.isBlank(user.getUserName()), "用户名不能为空!");
        AssertUtil.isTrue(StringUtils.isBlank(user.getUserPwd()),"用户密码不能为空!");
        User temp = userDao.queryUserByUserName(user.getUserName());
        AssertUtil.isTrue(null != temp, "该用户已存在!");
        AssertUtil.isTrue(userDao.save(user)<1,"用户记录添加失败!");
    }

    public void updateUser(User user) {
        AssertUtil.isTrue(StringUtils.isBlank(user.getUserName()), "用户名不能为空!");
        AssertUtil.isTrue(StringUtils.isBlank(user.getUserPwd()),"用户密码不能为空!");
        User temp = userDao.queryUserByUserName(user.getUserName());
        AssertUtil.isTrue(null != temp && !(temp.getId().equals(user.getId())), "该用户已存在!");
        AssertUtil.isTrue(userMapper.update(user)<1,"用户记录添加失败!");
    }

    public  void deleteUser(Integer id){
        AssertUtil.isTrue(null == id || null ==userDao.queryById(id),"待删除记录不存在!");
        AssertUtil.isTrue(userDao.delete(id)<1,"用户删除失败!");
    }

    public PageInfo<User> queryUserByParams(UserQuery userQuery){
        PageHelper.startPage(userQuery.getPageNum(),userQuery.getPageSize());
        return new PageInfo<User>(userDao.selectByParams(userQuery));
    }
}
