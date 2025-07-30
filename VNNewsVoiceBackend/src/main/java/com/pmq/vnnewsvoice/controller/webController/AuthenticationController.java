package com.pmq.vnnewsvoice.controller.webController;

import com.pmq.vnnewsvoice.service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class AuthenticationController{
    @Autowired
    private UserInfoService userInfoService;

    @RequestMapping("/login")
    public String login(){
        return "authentication/login";
    }
}
