package com.pmq.vnnewsvoice.service.impl;

import com.pmq.vnnewsvoice.pojo.UserInfo;
import com.pmq.vnnewsvoice.repository.UserInfoRepository;
import com.pmq.vnnewsvoice.service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.*;

@Service
@Transactional
public class UserInfoServiceImpl implements UserInfoService {

    @Autowired
    private UserInfoRepository userInfoRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Override
    public UserInfo addUser(UserInfo userinfo) {
        if (    userinfo == null ||
                userinfo.getUsername().isEmpty() ||
                userinfo.getPassword().isEmpty() ||
                userinfo.getPhoneNumber().isEmpty()){
            return null;
        }
        userinfo.setPassword(passwordEncoder.encode(userinfo.getPassword()));
        // Xử lý upload cloudinary ở đây

        return userInfoRepository.addUser(userinfo);
    }

    // Thêm phương thức process avatar ở đây



    @Override
    public Optional<UserInfo> getUserById(Long id) {
        if(id <= 0 || id == null){
            return Optional.of(null);
        }
        return userInfoRepository.getUserById(id);
    }

    @Override
    public Optional<UserInfo> getUserByUsername(String username) {
        if(username.isEmpty()){
            return Optional.of(null);
        }
        return userInfoRepository.getUserByUsername(username);
    }

    @Override
    public List<UserInfo> getUsers(Map<String, String> params) {
        return userInfoRepository.getUsers(params);
    }

    @Override
    public List<UserInfo> searchUsers(Map<String, String> filters, Map<String, String> params) {
        return userInfoRepository.searchUsers(filters,params);
    }


    @Override
    public boolean updateUser(UserInfo userinfo) {
        if(userinfo == null || getUserById(userinfo.getId()) == null){
            return false;
        }
        return userInfoRepository.updateUser(userinfo);
    }

    @Override
    public boolean deleteUser(Long id) {
        if (getUserById(id).isEmpty()){
            return false;
        }
        return userInfoRepository.deleteUser(id);
    }

    @Override
    public long countUsers(Map<String, String> params) {
        return userInfoRepository.countUsers(params);
    }


    @Override
    public long countSearchUsers(Map<String, String> filters, Map<String, String> params) {
        return userInfoRepository.countSearchUsers(filters,params);
    }
}
