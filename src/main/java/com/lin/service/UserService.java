package com.lin.service;




import com.lin.Entity.user;

import java.util.Map;

public interface UserService {
    Map<String, String> UserLogin(String state, String user_phone, String password);
    Map<String, String> RegisterUser(String state,user user);
    Map<String, String> ChangePassword(String state,String tel,String idnumber,String orpassword, String newpassword);
}
