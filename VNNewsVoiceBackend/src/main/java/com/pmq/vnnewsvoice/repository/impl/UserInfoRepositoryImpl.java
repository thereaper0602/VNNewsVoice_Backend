package com.pmq.vnnewsvoice.repository.impl;

import com.pmq.vnnewsvoice.pojo.UserInfo;
import com.pmq.vnnewsvoice.repository.UserInfoRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
@Transactional
public class UserInfoRepositoryImpl implements UserInfoRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Override
    public UserInfo addUser(UserInfo userinfo) {
        entityManager.persist(userinfo);
        return userinfo;
    }

    @Override
    public Optional<UserInfo> getUserById(Long id) {
        if(id == null || id <= 0){
            return Optional.empty();
        }
        return Optional.ofNullable(entityManager.find(UserInfo.class, id));
    }

    @Override
    public Optional<UserInfo> getUserByUsername(String username) {
        String hql = "FROM UserInfo ui WHERE ui.username = :username";
        List<UserInfo> userInfos = entityManager.createQuery(hql,UserInfo.class)
                .setParameter("username", username)
                .getResultList();
        return userInfos.isEmpty() ? Optional.empty() : Optional.of(userInfos.get(0));
    }

    @Override
    public List<UserInfo> getUsers(Map<String, String> params) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<UserInfo> query = builder.createQuery(UserInfo.class);
        Root<UserInfo> root = query.from(UserInfo.class);
        query.select(root);

        List<Predicate> predicates = buildSearchPredicates(params, builder, root);

        if(!predicates.isEmpty()){
            query.where(predicates.toArray(new Predicate[0]));
        }

        TypedQuery<UserInfo> q = entityManager.createQuery(query);

        if(params != null){
            int page = Integer.parseInt(params.getOrDefault("page","1"));
            int pageSize = Integer.parseInt(params.getOrDefault("pageSize","10"));
            page = Math.max(1,page);
            int start = (page - 1) * pageSize;
            q.setFirstResult(start);
            q.setMaxResults(pageSize);
        }
        return q.getResultList();
    }


    @Override
    public List<UserInfo> searchUsers(Map<String, String> filters, Map<String, String> params) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<UserInfo> query = builder.createQuery(UserInfo.class);
        Root<UserInfo> root = query.from(UserInfo.class);
        query.select(root);

        List<Predicate> predicates = buildSearchPredicates(filters, builder, root);
        if(!predicates.isEmpty()){
            query.where(predicates.toArray(new Predicate[0]));
        }

        TypedQuery<UserInfo> q = entityManager.createQuery(query);

        if(params != null){
            int page = Integer.parseInt(params.getOrDefault("page","1"));
            int pageSize = Integer.parseInt(params.getOrDefault("pageSize", "10"));
            page = Math.max(1, page);
            int start = (page - 1) * pageSize;
            q.setFirstResult(start);
            q.setMaxResults(pageSize);
        }

        return q.getResultList();
    }

    @Override
    public boolean updateUser(UserInfo userinfo) {
        if (getUserById(userinfo.getId()).isPresent()){
            entityManager.merge(userinfo);
            return true;
        }
        return false;
    }

    @Override
    public boolean deleteUser(Long id) {
        Optional<UserInfo> userInfo = getUserById(id);
        if(!userInfo.isEmpty()){
            entityManager.remove(userInfo.get());
            return true;
        }
        return false;
    }

    @Override
    public long countUsers(Map<String, String> params) {
        String hql = "SELECT COUNT(*) FROM UserInfo ui";
        return entityManager.createQuery(hql, Long.class).getSingleResult();
    }

    @Override
    public long countSearchUsers(Map<String, String> filters, Map<String, String> params) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> query = builder.createQuery(Long.class);
        Root<UserInfo> root = query.from(UserInfo.class);
        query.select(builder.count(root));

        List<Predicate> predicates = buildSearchPredicates(filters, builder, root);
        if(!predicates.isEmpty()){
            query.where(predicates.toArray(new Predicate[0]));
        }

        return entityManager.createQuery(query).getSingleResult();
    }

    @Override
    public boolean authenticateUser(String username, String password) {
        Optional<UserInfo> u = getUserByUsername(username);

        if (u.isEmpty()){
            return false;
        }

        return passwordEncoder.matches(password, u.get().getPassword());

    }

    @Override
    public List<Predicate> buildSearchPredicates(Map<String, String> filters, CriteriaBuilder builder, Root<UserInfo> root) {
        List<Predicate> predicates = new ArrayList<>();

        if(!filters.isEmpty()){
            if(filters.containsKey("username")){
                String keyword = "%" + filters.get("username").replace("_","\\_").replace("%","\\%") + "%";
                predicates.add(builder.like(root.get("username"),keyword,'\\'));
            }
            if(filters.containsKey("email")){
                String keyword = "%" + filters.get("username").replace("_", "\\_").replace("%","\\%") + "%";
                builder.like(root.get("email"),keyword);
            }
            if(filters.containsKey("isActive")){
                Boolean isActive = Boolean.parseBoolean(filters.get("isActive"));
                predicates.add(builder.equal(root.get("isActive"), isActive));
            }
            if(filters.containsKey("role")){
                if(!filters.get("role").equals("all")){
                    predicates.add(builder.equal(root.get("roleId").get("name"),filters.get("role")));
                }
            }
            if(filters.containsKey("roleFilter")){
                String[] roles = filters.get("roleFilter").split(",");
                List<Predicate> rolePredicates = new ArrayList<>();
                for(String role : roles) {
                    rolePredicates.add(builder.equal(root.get("roleId").get("name"), "ROLE_" + role));
                }
                predicates.add(builder.or(rolePredicates.toArray(new Predicate[0])));
            }
        }
        return predicates;
    }
}
