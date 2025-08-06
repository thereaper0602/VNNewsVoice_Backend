package com.pmq.vnnewsvoice.repository.impl;

import com.pmq.vnnewsvoice.pojo.Admin;
import com.pmq.vnnewsvoice.repository.AdminRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
@Transactional
public class AdminRepositoryImpl implements AdminRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Admin addAdmin(Admin admin) {
        entityManager.persist(admin);
        return admin;
    }

    @Override
    public Optional<Admin> getAdminById(Long id) {
        if(id < 0 || id == null){
            return Optional.empty();
        }
        return Optional.of(entityManager.find(Admin.class, id));
    }

    @Override
    public Optional<Admin> getAdminByUserId(Long userId) {
        String hql = "FROM Admin a WHERE a.userId.id = :userId";
        List<Admin> adminList = entityManager.createQuery(hql, Admin.class).setParameter("userId", userId).getResultList();
        return adminList.isEmpty() ? Optional.empty() : Optional.of(adminList.get(0));
    }

    @Override
    public Admin updateAdmin(Admin admin) {
        if(admin == null || admin.getId() == null){
            throw new IllegalArgumentException("Admin or Admin ID cannot be null");
        }
        Admin existingAdmin = entityManager.find(Admin.class, admin.getId());
        if(existingAdmin != null){
            entityManager.merge(admin);
            return admin;
        }
        return null;
    }

    @Override
    public boolean deleteAdmin(Long id) {
        if(id <= 0 || id == null){
            return false;
        }
        Admin admin = entityManager.find(Admin.class, id);
        if(admin != null){
            entityManager.remove(admin);
            return true;
        }
        return false;
    }

    @Override
    public long countAdmins() {
        return 0;
    }

    @Override
    public long countSearchAdmins(Map<String, String> filters) {
        return 0;
    }
}
