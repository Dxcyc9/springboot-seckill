package com.lin.service;



import com.lin.Entity.Commodity;
import com.lin.Entity.Commodity2;

import java.util.List;
import java.util.Map;

public interface CommodityService {
    Map<String,String> UploadCommodity(String state,Integer id);
    Integer ShowId();
    Map<String,String> BuyGoods(String state,String tel,int id) throws Exception;
    List<Commodity> ShowAssets(String state ,String usertel);
    Commodity2 getCommodityByid(int id);
    Map<String,String> BuySuccess(String tel,int id);
}
