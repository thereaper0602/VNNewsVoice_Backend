package com.pmq.vnnewsvoice.repository.impl;

import com.pmq.vnnewsvoice.pojo.UserInfo;
import com.pmq.vnnewsvoice.repository.UserInfoRepository;
import org.apache.catalina.User;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
@Transactional
public class UserInfoRepositoryImpl implements UserInfoRepository {
    @Override
    public UserInfo addUser(UserInfo Userinfo) {
        return null;
    }

    @Override
    public Optional<UserInfo> getUserById(Long id) {
        return Optional.empty();
    }

    @Override
    public Optional<UserInfo> getUserByUsername(String username) {
        return Optional.empty();
    }

    @Override
    public List<UserInfo> getUsers(Map<String, String> params) {
        return List.of();
    }

    @Override
    public List<UserInfo> getUsersByRole(String roleName) {
        return List.of();
    }

    @Override
    public List<UserInfo> searchUsers(Map<String, String> filters, Map<String, String> params) {
        return List.of();
    }

    @Override
    public List<User> getActiveUsers(Map<String, String> params) {
        return List.of();
    }

    @Override
    public Optional<UserInfo> updateUser(UserInfo Userinfo) {
        return Optional.empty();
    }

    @Override
    public Optional<UserInfo> deleteUser(Long id) {
        return Optional.empty();
    }

    @Override
    public long countUsers(Map<String, String> params) {
        return 0;
    }

    @Override
    public long countUsersByRole(String roleName) {
        return 0;
    }

    @Override
    public long countSearchUsers(Map<String, String> filters, Map<String, String> params) {
        return 0;
    }

    @Override
    public long countActiveUsers(Map<String, String> params) {
        return 0;
    }

    @Override
    public boolean authenticateUser(String username, String password) {
        return false;
    }
}
