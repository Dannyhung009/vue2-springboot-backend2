package com.example.mapper;

import com.example.entity.User;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface UserMapper {

    @Select("SELECT * FROM sys_user")
    List<User> findAll();

    @Insert("INSERT INTO `sys_user` (username,password,nickname,email,phone,address) " +
            "values (#{username},#{password},#{nickname},#{email},#{phone},#{address})")
    Integer insert(User user);


    Integer update(User user);


    @Delete("delete from sys_user where id = ${id}")
    Integer deleteById(@Param("id") Integer id);
}
