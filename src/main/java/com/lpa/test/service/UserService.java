package com.lpa.test.service;

import com.lpa.test.entity.User;

import java.util.List;

public interface UserService {

    User login(String username, String password);

    User findOne(Integer id);
}
