package com.lin.mapper;

import com.lin.Entity.Commodity2;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface AdministratorMapper {

    //查询是否存在该username
    @Select("select username from administrator where username = #{arg0}")
    String checkUsername(String username);


    //查询是否存在该usertel
    @Select("select usertel from administrator where usertel = #{arg0}")
    String checkUsertel(String usertel);
    //查询是否密码与用户名匹配

    @Select("select password from administrator where username = #{arg0}")
    String checkPassword(String username);

    //查询是否有重复tel与username
    @Select("select username from administrator where username = #{arg0} or usertel = #{arg1}")
    String checknametel(String username,String usertel);

    //添加该用户
    @Insert("insert into administrator (username,password,usertel) values (#{arg0},#{arg1},#{arg2})")
    int addUser(String username,String password,String usertel);

    //添加产品信息
    @Insert("insert into commodity (id,name,description,interest,lastingtime,starttime,num) values (#{arg0},#{arg1},#{arg2},#{arg3},#{arg4},#{arg5},#{arg6})")
    int addGoods(Integer id,String name,String description,String interest,String lastingtime,String starttime,Integer num);

    //添加产品数量
    @Insert("insert into commodity (num) values (#{arg1}) where id = #{arg0}")
    int addGoodsnum(Integer id,Integer num);

    //删除产品信息
    @Delete("delete from commodity where id = #{arg0}")
    int deleteGoods(Integer id);

    @Select("select * from commodity where name = #{arg0}")
    List<Commodity2> searchGoods(String name);


    @Select("select * from commodity where id = #{arg0}")
    List<Commodity2> searchGoods2(String id);


    //查询所有商品
    @Select("select * from commodity")
    List<Commodity2> ShowGoods();
}
