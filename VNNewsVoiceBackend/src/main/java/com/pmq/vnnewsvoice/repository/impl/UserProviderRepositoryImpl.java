package com.pmq.vnnewsvoice.repository.impl;

import com.pmq.vnnewsvoice.pojo.UserProvider;
import com.pmq.vnnewsvoice.repository.UserProviderRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public class UserProviderRepositoryImpl implements UserProviderRepository {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public UserProvider save(UserProvider userProvider) {
        if(userProvider.getId() == null){
            entityManager.persist(userProvider);
            return userProvider;
        }
        else{
            return entityManager.merge(userProvider);
        }
    }

    @Override
    public UserProvider getUserProviderById(Long id) {
        return null;
    }

    @Override
    public Optional<UserProvider> findByProviderAndProviderType(String providerId, String providerType) {
        String hql = "FROM UserProvider up WHERE up.providerId = :providerId AND up.providerType = :providerType";
        TypedQuery<UserProvider> query = entityManager.createQuery(hql, UserProvider.class);
        query.setParameter("providerId", providerId);
        query.setParameter("providerType", providerType);
        List<UserProvider> results = query.getResultList();
        return results.isEmpty() ? Optional.empty() : Optional.of(results.get(0));
    }


}
