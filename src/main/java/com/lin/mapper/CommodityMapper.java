package com.lin.mapper;

import com.lin.Entity.Commodity;

import com.lin.Entity.Commodity2;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;


@Mapper
public interface CommodityMapper {

    //查询是否存在重复id

    @Select("select * from commodity where id = #{arg0}")
    Commodity CheckId(Integer id);


    @Select("select MAX(id) from commodity")
    Integer MaxId();

    @Select("select working from user_information where tel = #{arg0}")
    Integer CheckWork(String tel);


    @Select("select laolai from user_information where tel = #{arg0}")
    Integer CheckLaolai(String tel);


    @Select("select age from user_information where tel = #{arg0}")
    Integer CheckAge(String tel);


    @Select("select nopromise_record from user_information where tel = #{arg0}")
    Integer CheckRecord(String tel);


    @Select("select num from commodity where id = #{arg0}")
    Integer CheckNum(int id);


    @Update("update commodity set num = num-1 where id = #{arg0}")
    int updateNum(int id);

    @Insert("insert into assets (usertel,goodsid) values (#{arg0},#{arg1})")
    int addAsset(String usertel,int goodsid);

    //查询是否买过该商品
    @Select("select usertel from assets where usertel = #{arg0} and goodsid = #{arg1}")
    String checkAsset(String usertel,int goodsid);

    //查询买过的所有商品信息
    @Select("SELECT * from commodity where id in (SELECT id from assets where usertel  = #{arg0})")
    List<Commodity> ShowAsset(String usertel);

    @Select("SELECT * from commodity")
    List<Commodity2> listGoodsVo();

    @Select("SELECT * from commodity where id =#{arg0}")
    Commodity2 getCommodityByid(int id);
}
