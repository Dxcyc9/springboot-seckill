package com.lin.rabbitmq;


import com.lin.Entity.Commodity2;
import com.lin.Entity.user;
import com.lin.mapper.CommodityMapper;
import com.lin.redis.RedisService;
import com.lin.service.CommodityService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by jiangyunxiong on 2018/5/29.
 */
@Service
public class MQReceiver {

    private static Logger log = LoggerFactory.getLogger(MQReceiver.class);

    @Autowired
    CommodityMapper commodityMapper;

    @Autowired
    RedisService redisService;

    @Autowired
    CommodityService commodityService;

//    @Autowired
//    OrderService orderService;
//
//    @Autowired
//    SeckillService seckillService;

    @RabbitListener(queues=MQConfig.QUEUE)
    public void receive(String message){

        log.info("receive message:"+message);
        SeckillMessage m = RedisService.stringToBean(message, SeckillMessage.class);
        user user = m.getUser();
        int goodsId = m.getGoodsId();

        Commodity2 goods = commodityService.getCommodityByid(goodsId);
        int stock = goods.getNum();
        if(stock <= 0){
            return ;
        }

//        //判断重复秒杀
//        SeckillOrder order = orderService.getOrderByUserIdGoodsId(user.getId(), goodsId);
//        if(order != null) {
//            return;
//        }

        //减库存 下订单 写入秒杀订单
        commodityService.BuySuccess(user.getTel(),goodsId);

        return ;
        //seckillService.seckill(user, goodsId);
    }

    @RabbitListener(queues = MQConfig.TOPIC_QUEUE1)
    public void receiveTopic1(String message) {
        log.info(" topic  queue1 message:" + message);
    }

    @RabbitListener(queues = MQConfig.TOPIC_QUEUE2)
    public void receiveTopic2(String message) {
        log.info(" topic  queue2 message:" + message);
    }
}
