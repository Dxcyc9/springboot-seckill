package com.lin.service;

import com.alibaba.fastjson.JSONObject;
import com.lin.Entity.Commodity;
import com.lin.Entity.Commodity2;
import com.lin.mapper.AdministratorMapper;
import com.lin.mapper.CommodityMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class AdministratorServiceImpl implements AdministratorService{

    @Autowired
    AdministratorMapper administratorMapper;
    @Autowired
    CommodityMapper commodityMapper;
    Commodity commodity;

    @Override
    public Map<String, String> AdministratorLogin(String state, String username, String password)
    {

        Map<String,String> map = new HashMap<>();
        if (!state.equals("login")) return null;
        else if(administratorMapper.checkUsername(username) == null){
            map.put("Login","false");
            map.put("username","no_name");
        }
        else if(!password.equals(administratorMapper.checkPassword(username))){
            map.put("Login","false");
            map.put("password","false");

        }
        else{
            map.put("Login","true");
            map.put("username",username);
        }
        return map;
    }

    @Override
    public Map<String, String> AdministratorRegister(String state, String username, String password, String usertel)
    {
        Map<String,String> map = new HashMap<>();
        if (!state.equals("register")) return null;
        else if(administratorMapper.checkUsername(username)!=null){
            map.put("register","false");
            map.put("username","repeat");
            if(administratorMapper.checkUsertel(usertel)!=null){
                map.put("usertel","repeat");
            }
        }
        else if(administratorMapper.checkUsertel(usertel)!=null){
            map.put("register","false");
            map.put("usertel","repeat");
        }
        else {
            administratorMapper.addUser(username,password,usertel);
            map.put("register","success");
            map.put("username",username);
        }
        return map;

    }

    @Override
    public Map<String ,String> UploadGoods(Integer id,String name,String description,String interest,String lastingtime,String starttime,Integer num)
    {
        Map<String,String> map= new HashMap<>();
        if((commodity = commodityMapper.CheckId(id)) != null){
            map.put("state","false");
            map.put("reason","存在重复id");
        }
        else{
            administratorMapper.addGoods(id,name,description,interest,lastingtime,starttime,num);
            //administratorMapper.addGoodsnum(id,num);
            map.put("state","success");
        }
        return map;
    }


    @Override
    public Map<String ,String> DeleteGoods(Integer id)
    {
        Map<String,String> map= new HashMap<>();
        administratorMapper.deleteGoods(id);
        map.put("state","success");
        return map;
    }

    @Override
    public List<Commodity2> SearchGoods(String some)
    {
        List<Commodity2> list = new ArrayList<>();
        Pattern pattern = Pattern.compile("[0-9]*");
        Matcher isNum = pattern.matcher(some);
        if (!isNum.matches()) {
            list.addAll(administratorMapper.searchGoods(some));
        }
        else{
            list.addAll(administratorMapper.searchGoods2(some));

        }
        return list;
    }

    @Override
    public List<Commodity2> ShowProduct()
    {
        List<Commodity2> list = new ArrayList<>();
        list.addAll(administratorMapper.ShowGoods());
        return list;
    }
}
