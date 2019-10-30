package com.lpa.test.service.impl;

import com.lpa.test.entity.User;
import com.lpa.test.repository.UserRepository;
import com.lpa.test.service.UserService;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private UserRepository userRepository;

    @Override
    public User login(String username,String password){
        User user = userRepository.findUserByNameAndPassword(username,password);
        return user;
    }


    @Override
    public User findOne(Integer id){
        Optional<User> optional = userRepository.findById(id);
        User user = optional.get();
        return user;
    }
}
