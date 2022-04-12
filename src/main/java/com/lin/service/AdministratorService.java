package com.lin.service;

import com.alibaba.fastjson.JSONObject;
import com.lin.Entity.Commodity2;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Map;

public interface AdministratorService {

    Map<String, String> AdministratorLogin(String state, String username, String password);
    Map<String, String> AdministratorRegister(String state, String username, String password, String usertel);
    Map<String ,String> UploadGoods(Integer id,String name,String description,String interest,String lastingtime,String starttime,Integer num);
    Map<String ,String> DeleteGoods(Integer id);
    List<Commodity2> SearchGoods(String some);
    List<Commodity2> ShowProduct();
}
