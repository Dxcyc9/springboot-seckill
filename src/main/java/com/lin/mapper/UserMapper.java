package com.lin.mapper;

import com.lin.Entity.user;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;


@Mapper
public interface UserMapper {
    /*
    登录时调用

    通过用户号码和密码查询用户信息是否正确
     */
    @Select("select * from user_information where tel=#{arg0} and password=#{arg1}")
    user checkUser(String userPhone,String password);


    @Insert("insert into user_information(tel,username,password,idnumber,bankcard) " +
            "values(#{tel},#{username},#{password},#{idnumber},#{bankcard})")
    int insertUser(user user);

    /*
    注册时调用

    通过用户名、密码查询是否存在该用户
     */
    @Select("select * from user_information where tel=#{arg0} ")
    user checkForUser(String userPhone);

//检查电话是否存在
    @Select("select tel from user_information where tel=#{arg0}")
    String checkTel(String tel);

    //根据电话查询idnumber
    @Select("select idnumber from user_information where tel=#{arg0}")
    String checkIdnumber(String tel);

    //检查电话相对应的password
    @Select("select password from user_information where tel=#{arg0}")
    String checkPassword(String tel);

    //修改password
    @Update("update user_information set password=#{arg1} where tel=#{arg0}")
    int updatePassword(String tel,String password);

}


