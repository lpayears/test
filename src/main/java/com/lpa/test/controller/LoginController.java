package com.lpa.test.controller;


import com.lpa.test.entity.User;
import com.lpa.test.service.UserService;
import com.lpa.test.utils.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.util.Map;

@Controller
public class LoginController {

    @Autowired
    private UserService userService;

    @PostMapping("/user/login")
    public String login(@RequestParam("username")String username, @RequestParam("password")String password,
                        Map<String,Object>map, HttpSession session){
        User user = userService.login(username,password);
        if(!ObjectUtils.isEmpty(user)){
            session.setAttribute("loginUser",username);
            return "redirect:/warn";
        }
        else{
            map.put("msg","用户名或密码错误");
            return  "login";
        }
    }
}
