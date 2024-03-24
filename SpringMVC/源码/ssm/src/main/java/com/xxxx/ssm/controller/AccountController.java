package com.xxxx.ssm.controller;

import com.xxxx.ssm.service.AccountService;
import com.xxxx.ssm.vo.Account;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;


/**
 * Restful  url 地址配置
 *   1.请求类型  GET POST  PUT  DELETE
 *   2.URL 设置  不体现动作成份(没有动词)  account/1   account/2 account
 *   3.参数格式
 *      路径参数
 *      json 格式| 普通表单参数
 *   4.响应内容:json
 */
@Controller
public class AccountController extends BaseController{

    @Resource
    private AccountService accountService;

    //@RequestMapping("account/queryAccountById")
    // ip:port/ssm/account/1
    @GetMapping("account/{id}")
    @ResponseBody
    public Account queryAccountById(@PathVariable Integer id){
        int a=1/0;
        return accountService.selectById(id);
    }



    @DeleteMapping("account/{id}")
    @ResponseBody
    public Map<String,Object> delAccountById(@PathVariable Integer id){
        Map<String,Object> map=new HashMap<String,Object>();
        if(accountService.delAccount(id)>0){
            map.put("code",200);
            map.put("msg","账户删除成功");

        }else{
            map.put("code",500);
            map.put("msg","账户删除失败");
        }
        return map;
    }


    /**
     * 资源添加操作-post
     * @param account   json格式
     * @return
     */
    @PostMapping("account")
    @ResponseBody
    public Map<String,Object> saveAccount(@RequestBody  Account account){
        Map<String,Object> map=new HashMap<String,Object>();
        if(accountService.saveAccount(account)>0){
            map.put("code",200);
            map.put("msg","账户添加成功");
        }else{
            map.put("code",500);
            map.put("msg","账户添加失败");
        }
        return map;
    }


    /**
     * 资源的更新-put
     * @param account  json
     * @return
     */
    @PutMapping("account")
    @ResponseBody
    public Map<String,Object> updateAccount(@RequestBody  Account account){
        Map<String,Object> map=new HashMap<String,Object>();
        if(accountService.updateAccount(account)>0){
            map.put("code",200);
            map.put("msg","账户更新成功");
        }else{
            map.put("code",500);
            map.put("msg","账户更新失败");
        }
        return map;
    }

}
