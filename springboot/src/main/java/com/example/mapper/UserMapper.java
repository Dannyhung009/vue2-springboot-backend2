package com.example.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.entity.User;
import org.apache.ibatis.annotations.*;

import java.util.List;

//@Mapper
public interface UserMapper extends BaseMapper<User> {

    //給Mybatis用
//    @Select("SELECT * FROM sys_user")
//    List<User> findAll();
//
//    @Insert("INSERT INTO `sys_user` (username,password,nickname,email,phone,address) " +
//            "values (#{username},#{password},#{nickname},#{email},#{phone},#{address})")
//    int insert(User user);
//
//
//    int update(User user);


//    @Delete("delete from sys_user where id = ${id}")
//    Integer deleteById(@Param("id") Integer id);

//    @Select("select * FROM `sys_user` where username LIKE #{username} limit #{pageNum},#{pageSize}")
//    List<User> selectPage(Integer pageNum, Integer pageSize, String username);

//    @Select("select count(*) from sys_user where username LIKE #{username}")
//    Integer selectTotal(String username);
}
