package com.xxxx.ssm.controller;

import com.xxxx.ssm.service.AccountService;
import com.xxxx.ssm.vo.Account;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

@Controller
public class AccountController {
    @Resource
    private AccountService accountService;

    @GetMapping("account/{id}")
    @ResponseBody
    public Account queryAccountById(@PathVariable  Integer id){
        return accountService.selectById(id);
    }
    @DeleteMapping("account/{id}")
    @ResponseBody
    public Map<String,Object> deleteAccount(@PathVariable Integer id){
        int result = accountService.delAccount(id);
        Map<String,Object> map=new HashMap<String,Object>();
        map.put("msg","success");
        map.put("code",200);
        if(result==0){
            map.put("msg","error");
            map.put("code",500);
        }
        return map;
    }
    /* restful-->post请求执行添加操作
     * @return
     */
    @PostMapping("account")
    @ResponseBody
    public Map<String,Object> saveAccount(@RequestBody  Account account){
        int result = accountService.saveAccount(account);
        Map<String,Object> map=new HashMap<String,Object>();
        map.put("msg","success");
        map.put("code",200);
        if(result==0){
            map.put("msg","error");
            map.put("code",500);
        }
        return map;
    }
    /* restful-->put 请求执行更新操作
     * @param id
     * @param account
     * @return
     */
    @PutMapping("account")
    @ResponseBody
    public Map<String,Object> updateAccount(@RequestBody  Account account){
        int result = accountService.updateAccount(account);
        Map<String,Object> map=new HashMap<String,Object>();
        map.put("msg","success");
        map.put("code",200);
        if(result==0){
            map.put("msg","error");
            map.put("code",500);
        }
        return map;
    }
}
