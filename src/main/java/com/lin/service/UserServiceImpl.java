package com.lin.service;

import com.alibaba.fastjson.JSONObject;
import com.lin.Entity.user;
import com.lin.mapper.UserMapper;
import com.lin.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    UserMapper userMapper;

/*
用户登录
 */
    @Override
    public Map<String, String> UserLogin(String state, String user_phone, String password) {
       if (!state.equals("login"))
        return null;
       else
       {
           user user ;

           Map<String,String> map = new HashMap<>();
           if (userMapper.checkTel(user_phone)==null){
               map.put("login_check","false");
               map.put("tel","no_tel");
           }
           else if((user = userMapper.checkUser(user_phone,password))==null)
           {

                map.put("login_check","false");
                map.put("password","false");
           }
           else
           {

               map.put("login_check","true");
               map.put("tel",user.getTel());

           }
           return map;
       }
    }

    @Override
    public Map<String, String> RegisterUser(String state,user user) {
        if(!state.equals("register"))return null;
        user user1;
        Map<String,String> map = new HashMap<>();
        if(userMapper.checkForUser(user.getTel())==null)
        {
            userMapper.insertUser(user);
            map.put("register_check","true");
            map.put("tel",user.getTel());
        }
        else
        {
            map.put("register_check","false");
            map.put("tel","repeat");
        }
      return map;
    }

    //修改密码
    @Override
    public Map<String, String> ChangePassword(String state,String tel,String idnumber,String orpassword, String newpassword)
    {
       // System.out.println("222");
        if(!state.equals("changepassword"))return null;
        Map<String,String> map = new HashMap<>();
        if(userMapper.checkTel(tel)==null){
            map.put("change_check","false");
            map.put("tel","no_tel");
        }
        else if(!idnumber.equals(userMapper.checkIdnumber(tel))){
            map.put("change_check","false");
            map.put("idnumber","false");
        }
        else if(!orpassword.equals(userMapper.checkPassword(tel))){
            map.put("change_check","false");
            map.put("orpassword","false");
        }
        else if(orpassword.equals(newpassword)){
            map.put("change_check","false");
            map.put("newpassword","repeat");
        }
        else {
            userMapper.updatePassword(tel,newpassword);
            map.put("change_check","true");
            map.put("newpassword",newpassword);
        }
        return map;
    }
}
