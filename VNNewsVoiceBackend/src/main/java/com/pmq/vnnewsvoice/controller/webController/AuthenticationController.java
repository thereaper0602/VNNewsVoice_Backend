package com.pmq.vnnewsvoice.controller.webController;

import com.pmq.vnnewsvoice.service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AuthenticationController{
    @Autowired
    private UserInfoService userInfoService;

    @GetMapping("/login")
    public String login(){
        return "authentication/login";
    }
}
