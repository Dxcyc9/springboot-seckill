package com.lin.controller;

import com.alibaba.fastjson.JSONObject;
import com.lin.Entity.user;
import com.lin.service.UserService;

import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;


@RestController()
public class UserController {

    @Autowired
    UserService userService;

    /*
    用户登录
     */
   
    @PostMapping("/UserLogin")
    Map<String, String> UserLogin(@RequestBody JSONObject jsonObject)
    {
      return   userService.UserLogin(jsonObject.getString("state"),jsonObject.getString("tel"),jsonObject.getString("password"));
    }

    /*
    用户注册
     */
    
    @PostMapping("/UserRegister")
    Map<String, String> UserRegister(@RequestBody@ApiParam() JSONObject jsonObject)
    {
        user user=new user(jsonObject.getString("username"),jsonObject.getString("tel"),jsonObject.getString("idnumber"),jsonObject.getString("password"),jsonObject.getString("bankcard"));
        System.out.println(user);
        return   userService.RegisterUser(jsonObject.getString("state"),user);
    }

    //修改密码
    @PostMapping("/ChangePassword")
    Map<String,String> ChangePassword(@RequestBody@ApiParam() JSONObject jsonObject)
    {
        System.out.println("111");
        return userService.ChangePassword(jsonObject.getString("state"),jsonObject.getString("tel"),jsonObject.getString("idnumber"),jsonObject.getString("orpassword"),jsonObject.getString("newpassword"));
    }
}
