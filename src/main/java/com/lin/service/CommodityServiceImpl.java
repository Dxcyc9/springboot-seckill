package com.lin.service;

import com.lin.Entity.Commodity;
import com.lin.Entity.Commodity2;
import com.lin.Entity.user;
import com.lin.mapper.CommodityMapper;
import com.lin.mapper.UserMapper;
import com.lin.rabbitmq.MQReceiver;
import com.lin.rabbitmq.MQSender;
import com.lin.rabbitmq.SeckillMessage;
import com.lin.redis.GoodsKey;
import com.lin.redis.RedisService;
import lombok.ToString;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.lin.Entity.user;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CommodityServiceImpl implements CommodityService, InitializingBean {
    @Autowired
    CommodityMapper commodityMapper;

    Commodity commodity;

    @Autowired
    MQReceiver mqReceiver;

    @Autowired
    RedisService redisService;

    @Autowired
    UserMapper userMapper;

//    user user;

    @Autowired
    MQSender sender;

    @Override
    public Commodity2 getCommodityByid(int id){
        return commodityMapper.getCommodityByid(id);
    }

    @Override
    public Map<String,String> UploadCommodity(String state,Integer id){
        Map<String,String> map = new HashMap<>();
        if (!state.equals("uploadcommdity"))
            return null;
        else if((commodity = commodityMapper.CheckId(id))==null)
        {
            map.put("upload_check","false");
            map.put("id","null");
        }
        else
        {
             map.put("upload_check","true");
             map.put("id",commodity.getId().toString());
             map.put("name",commodity.getName());
             map.put("description",commodity.getDescription());
             map.put("interest",commodity.getInterest());
             map.put("lastingtime",commodity.getLastingtime());
             map.put("starttime",commodity.getStarttime());
        }
        return map;
    }


    @Override
    public Integer ShowId(){
        return commodityMapper.MaxId();
    }

    @Override
    public Map<String,String> BuyGoods(String state,String tel,int id) throws Exception {
        user user1 = userMapper.checkForUser(tel);
        Map<String,String> map = new HashMap<>();
        if (!state.equals("buyGoods"))
            return null;
        else if(commodityMapper.CheckAge(tel)<18){
            map.put("status","0");
            map.put("reason1","age<18");

            if(commodityMapper.CheckLaolai(tel)==1){
                map.put("reason2","位于失信人名单");
            }
            if(commodityMapper.CheckRecord(tel)>2){
                map.put("reason3","近3年逾期2次以上");
            }
            if(commodityMapper.CheckWork(tel)==0){
                map.put("reason4","unemployment");
            }
        }
        else if(commodityMapper.CheckLaolai(tel)==1){
            map.put("status","0");
            map.put("reason1","位于失信人名单");
            if(commodityMapper.CheckRecord(tel)>2){
                map.put("reason2","近3年逾期2次以上");
            }
            if(commodityMapper.CheckWork(tel)==0){
                map.put("reason3","unemployment");
            }
        }
        else if(commodityMapper.CheckRecord(tel)>2){
            map.put("status","0");
            map.put("reason1","近3年逾期2次以上");
            if(commodityMapper.CheckWork(tel)==0){
                map.put("reason2","unemployment");
            }
        }
        else if(commodityMapper.CheckWork(tel)==0){
            map.put("status","0");
            map.put("reason1","unemployment");
        }
//        else if(commodityMapper.CheckNum(id)<=0){
//            map.put("status","0");
//            map.put("reason1","sell out");
//        }

        else if (commodityMapper.checkAsset(tel,id)!=null){
            map.put("status","0");
            map.put("reason1","您已购买过一件");
        }
        else{
            long stock = redisService.decr(GoodsKey.getGoodsStock, "" + id);//10
            if (stock < 0) {
                redisService.incr(GoodsKey.getGoodsStock,""+id);
                map.put("status","0");
                map.put("reason1","sell out");
            }
            else{
                SeckillMessage message = new SeckillMessage();
                message.setUser(user1);
                message.setGoodsId(id);
                sender.sendSeckillMessage(message);
//            commodityMapper.updateNum(id);
//                commodityMapper.addAsset(tel,id);
                map.put("status","1");
                map.put("tel",tel);
                map.put("goodsid",String.valueOf(id) );

               // commodityMapper.addAsset(tel,id);
            }



        }
        return map;
    }

    @Override
    public Map<String,String> BuySuccess(String tel,int id){
        System.out.println("buy");
        Map<String,String> map = new HashMap<>();
        commodityMapper.updateNum(id);
        commodityMapper.addAsset(tel,id);
        map.put("status","1");
        map.put("tel",tel);
        map.put("goodsid",String.valueOf(id) );
        return map;
    }

    @Override
    public List<Commodity> ShowAssets(String state , String usertel)
    {
        List<Commodity> list = new ArrayList<>();
        list.addAll(commodityMapper.ShowAsset(usertel));
        return list;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        List<Commodity2> goodsVoList = commodityMapper.listGoodsVo();
        if (goodsVoList == null) {
            return;
        }
        for (Commodity2 goods : goodsVoList) {
            redisService.set(GoodsKey.getGoodsStock, "" + goods.getId(), goods.getNum());
            //初始化商品都是没有处理过的
           // localOverMap.put(goods.getId(), false);
        }
    }
}
