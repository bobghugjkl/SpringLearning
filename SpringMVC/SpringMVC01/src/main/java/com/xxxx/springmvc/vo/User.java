package com.xxxx.springmvc.vo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class User {
    private Integer id;
    private String userName;
    private String userPwd;
//    private List <Integer>ids;
//    public Map<String,String>map = new HashMap<>();

//    public Map<String, String> getMap() {
//        return map;
//    }
//
//    public void setMap(Map<String, String> map) {
//        this.map = map;
//    }
//
//    public List<Integer> getIds() {
//        return ids;
//    }
//
//    public void setIds(List<Integer> ids) {
//        this.ids = ids;
//    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String username) {
        this.userName = username;
    }

    public String getUserPwd() {
        return userPwd;
    }

    public void setUserPwd(String userPwd) {
        this.userPwd = userPwd;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + userName + '\'' +
                ", userPwd='" + userPwd + '\'' +
                '}';
    }
}
