package com.pmq.vnnewsvoice.service.impl;

import com.pmq.vnnewsvoice.pojo.CustomUserDetails;
import com.pmq.vnnewsvoice.pojo.UserInfo;
import com.pmq.vnnewsvoice.repository.UserInfoRepository;
import com.pmq.vnnewsvoice.service.UserDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service("userDetailsService")
@Transactional
public class UserDetailServiceImpl implements UserDetailService {
    @Autowired
    private UserInfoRepository userInfoRepository;



    @Override
    public boolean authenticateUser(String username, String password) {
        return userInfoRepository.authenticateUser(username,password);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<UserInfo> u = userInfoRepository.getUserByUsername(username);
        if(u.isEmpty()){
            throw new UsernameNotFoundException("Invalid username!");
        }

        return new CustomUserDetails(u.get());

    }
}
