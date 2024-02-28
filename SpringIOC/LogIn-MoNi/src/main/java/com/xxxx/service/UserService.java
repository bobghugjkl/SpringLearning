package com.xxxx.service;

import com.xxxx.dao.UserDao;
import com.xxxx.entity.User;
import com.xxxx.entity.vo.MessageModel;
import com.xxxx.utils.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class UserService {
    @Resource
    private UserDao userDao;
    public MessageModel checkUserLogin(String uname,String upwd){
        MessageModel messageModel = new MessageModel();
        if(StringUtils.isEmpty(uname)||StringUtils.isEmpty(upwd)){
            messageModel.setResultcode(0);
            messageModel.setResultMessage("不能为空");
            return messageModel;
        }
        User user = userDao.queryUserByUsername(uname);
        if(user==null){
            messageModel.setResultcode(0);
            messageModel.setResultMessage("不存在");
            return messageModel;
        }
        if(!upwd.equals(user.getUserpassword())){
            messageModel.setResultcode(0);
            messageModel.setResultMessage("password isn't right");
            return messageModel;
        }
        messageModel.setResultMessage("成功");
        return messageModel;
    }
}
