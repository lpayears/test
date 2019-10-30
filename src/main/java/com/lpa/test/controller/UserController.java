package com.lpa.test.controller;

import com.lpa.test.entity.User;
import com.lpa.test.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @ModelAttribute
    public void getUser(@RequestParam(value = "id",required = false)Integer id, Map<String,Object> map){
        if(id != null){
            User user =userService.findOne(id);
            map.put("user",user);
        }


    }
}
