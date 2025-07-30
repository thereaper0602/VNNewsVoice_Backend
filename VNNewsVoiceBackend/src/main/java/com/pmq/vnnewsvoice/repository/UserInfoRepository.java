package com.pmq.vnnewsvoice.repository;

import com.pmq.vnnewsvoice.pojo.UserInfo;
import com.pmq.vnnewsvoice.pojo.UserProvider;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface UserInfoRepository {
    UserInfo addUser(UserInfo Userinfo);

    Optional<UserInfo> getUserById(Long id);
    Optional<UserInfo> getUserByUsername(String username);
    List<UserInfo> getUsers(Map<String, String> params);
    List<UserInfo> searchUsers(Map<String, String> filters, Map<String, String> params);

    boolean updateUser(UserInfo Userinfo);
    boolean deleteUser(Long id);

    long countUsers(Map<String, String> params);
    long countSearchUsers(Map<String, String> filters, Map<String, String> params);

    boolean authenticateUser(String username, String password);
    List<Predicate> buildSearchPredicates(Map<String, String> filters, CriteriaBuilder builder, Root<UserInfo> root);
}
