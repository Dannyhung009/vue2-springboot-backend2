package com.example.service;

import com.example.controller.dto.UserDto;
import com.example.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author danny
 * @since 2022-12-20
 */
public interface IUserService extends IService<User> {


    Boolean login(UserDto userdto);
}
