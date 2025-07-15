package com.pmq.vnnewsvoice.repository;

import com.pmq.vnnewsvoice.pojo.UserInfo;
import com.pmq.vnnewsvoice.pojo.UserProvider;
import org.apache.catalina.User;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface UserInfoRepository {
    UserInfo addUser(UserInfo Userinfo);

    Optional<UserInfo> getUserById(Long id);
    Optional<UserInfo> getUserByUsername(String username);
    List<UserInfo> getUsers(Map<String, String> params);
    List<UserInfo> getUsersByRole(String roleName);
    List<UserInfo> searchUsers(Map<String, String> filters, Map<String, String> params);
    List<User> getActiveUsers(Map<String, String> params);

    Optional<UserInfo> updateUser(UserInfo Userinfo);
    Optional<UserInfo> deleteUser(Long id);

    long countUsers(Map<String, String> params);
    long countUsersByRole(String roleName);
    long countSearchUsers(Map<String, String> filters, Map<String, String> params);
    long countActiveUsers(Map<String, String> params);

    boolean authenticateUser(String username, String password);
}
