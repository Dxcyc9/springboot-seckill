package com.lin.controller;

import com.alibaba.fastjson.JSONObject;
import com.lin.Entity.Commodity;
import com.lin.Entity.Commodity2;
import com.lin.service.AdministratorService;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
public class AdministratorController {

    @Autowired
    AdministratorService administratorService;
    //登录
    @PostMapping("/AdministratorLogin")
    Map<String, String> AdministratorLogin(@RequestBody JSONObject jsonObject)
    {
        return   administratorService.AdministratorLogin(jsonObject.getString("state"),jsonObject.getString("username"),jsonObject.getString("password"));
    }

    //注册
    @PostMapping("/AdministratorRegister")
    Map<String, String> AdministratorRegister(@RequestBody@ApiParam() JSONObject jsonObject)
    {

        return   administratorService.AdministratorRegister(jsonObject.getString("state"),jsonObject.getString("username"),jsonObject.getString("password"), jsonObject.getString("usertel"));
    }


    //上传产品
    @PostMapping("/UploadGoods")
    Map<String ,String> UploadGoods(@RequestBody JSONObject jsonObject)
    {
        return administratorService.
                UploadGoods(jsonObject.getInteger("id"),
                            jsonObject.getString("name"),
                            jsonObject.getString("description"),
                            jsonObject.getString("interest"),
                            jsonObject.getString("lastingtime"),
                            jsonObject.getString("starttime"),
                            jsonObject.getInteger("num")
                );
    }

    //删除产品
    @PostMapping("DeleteGoods")
    Map<String,String> DeleteGoods(@RequestBody JSONObject jsonObject)
    {
        return administratorService.DeleteGoods(jsonObject.getInteger("id"));
    }


    //搜索产品
    @PostMapping("SearchGoods")
    List<Commodity2> SearchGoods(@RequestBody JSONObject jsonObject)
    {
        return administratorService.SearchGoods(jsonObject.getString("id/name"));
    }

    //产品列表
    @GetMapping("ShowProduct")
    List<Commodity2> ShowProduct()
    {
        return administratorService.ShowProduct();
    }
}
