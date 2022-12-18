package com.example.service;

import com.example.entity.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserService {


    Boolean saveUser(User user);

    List<User> findAll();
}
