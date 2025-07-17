package com.pmq.vnnewsvoice.service;

import com.pmq.vnnewsvoice.pojo.UserInfo;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface UserInfoService {
    UserInfo addUser(UserInfo Userinfo);

    Optional<UserInfo> getUserById(Long id);
    Optional<UserInfo> getUserByUsername(String username);
    List<UserInfo> getUsers(Map<String, String> params);
    List<UserInfo> getUsersByRole(String roleName);
    List<UserInfo> searchUsers(Map<String, String> filters, Map<String, String> params);
    List<UserInfo> getActiveUsers(Map<String, String> params);

    Optional<UserInfo> updateUser(UserInfo Userinfo);
    Optional<UserInfo> deleteUser(Long id);

    long countUsers(Map<String, String> params);
    long countUsersByRole(String roleName);
    long countSearchUsers(Map<String, String> filters, Map<String, String> params);
    long countActiveUsers(Map<String, String> params);

    boolean authenticateUser(String username, String password);
}
