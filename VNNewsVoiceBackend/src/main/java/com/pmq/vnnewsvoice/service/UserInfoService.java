package com.pmq.vnnewsvoice.service;

import com.pmq.vnnewsvoice.pojo.UserInfo;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface UserInfoService{
    UserInfo addUser(UserInfo Userinfo);

    Optional<UserInfo> getUserById(Long id);
    Optional<UserInfo> getUserByUsername(String username);
    List<UserInfo> getUsers(Map<String, String> params);
    List<UserInfo> searchUsers(Map<String, String> filters, Map<String, String> params);

    boolean updateUser(UserInfo Userinfo);
    boolean deleteUser(Long id);

    long countUsers(Map<String, String> params);
    long countSearchUsers(Map<String, String> filters, Map<String, String> params);

}
