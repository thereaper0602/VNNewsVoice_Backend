package com.pmq.vnnewsvoice.service;

import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserDetailService extends UserDetailsService {
    boolean authenticateUser(String username, String password);
}
