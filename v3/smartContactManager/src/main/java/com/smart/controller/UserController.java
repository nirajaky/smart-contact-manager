package com.smart.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/user")
public class UserController {

    @RequestMapping("/index")             // When user will fire : /user/index = then only this controller will work
    public String dashboard(){
        return "normal/user_dashboard";
    }
}
