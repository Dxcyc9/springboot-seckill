package com.lin.controller;



import com.alibaba.fastjson.JSONObject;
import com.lin.Entity.Commodity;
import com.lin.result.Result;
import com.lin.service.CommodityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RestController

public class CommodityController {

    @Autowired
    CommodityService commodityService;

    @PostMapping("UploadCommodity")
    Result<Map<String,String>> UploadCommodity(@RequestBody JSONObject jsonObject)
    {
        Map<String,String> map = commodityService.UploadCommodity(jsonObject.getString("state"),jsonObject.getInteger("id"));
        Map<String,String> map1=new HashMap<>();
        map1.put("1","1");


        return Result.success(map);
    }


    @GetMapping("ShowId")
    Integer ShowId()
    {
        return commodityService.ShowId();
    }


    @PostMapping("BuyGoods")
    Map<String,String> BuyGoods(@RequestBody JSONObject jsonObject) throws Exception {
        return commodityService.BuyGoods(jsonObject.getString("state"),jsonObject.getString("tel"),jsonObject.getInteger("id"));
    }

    @PostMapping("ShowAssets")
    List<Commodity> ShowAssets(@RequestBody JSONObject jsonObject)
    {
        return commodityService.ShowAssets(jsonObject.getString("state"),jsonObject.getString("usertel"));
    }
}
