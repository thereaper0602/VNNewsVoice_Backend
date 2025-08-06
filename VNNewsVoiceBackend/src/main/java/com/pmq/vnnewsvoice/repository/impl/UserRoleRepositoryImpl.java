package com.pmq.vnnewsvoice.repository.impl;

import com.pmq.vnnewsvoice.pojo.Role;
import com.pmq.vnnewsvoice.repository.UserRoleRepository;
import jakarta.persistence.*;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Repository
@Transactional
public class UserRoleRepositoryImpl implements UserRoleRepository {

    @PersistenceContext
    private EntityManager entityManager;


    @Override
    @Transactional(readOnly = true)
    public List<Role> getAllUserRoles() {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Role> query = builder.createQuery(Role.class);
        Root<Role> root = query.from(Role.class);
        query.select(root);
        return entityManager.createQuery(query).getResultList();
    }

    @Override
    @Transactional(readOnly = true)
    public Role getUserRoleByName(String name) {
        try{
            TypedQuery<Role> query = entityManager.createNamedQuery("Role.findByName", Role.class);
            query.setParameter("name", name);
            return query.getSingleResult();
        }
        catch (NoResultException e){
            return null;
        }
    }

    @Override
    public Role getUserRoleById(Long id) {
        return entityManager.find(Role.class, id);
    }

    @Override
    public Role addUserRole(Role newRole) {
        entityManager.persist(newRole);
        return newRole;
    }

    @Override
    public Role updateUserRole(Role role) {
        if(getUserRoleById(role.getId()) != null){
            entityManager.merge(role);
            return role;
        }
        return null;
    }

    @Override
    public boolean deleteUserRole(Long id) {
        Role existingRole = getUserRoleById(id);
        if(existingRole != null){
            entityManager.remove(existingRole);
            return true;
        }
        return false;
    }


}
