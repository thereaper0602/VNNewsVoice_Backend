package com.pmq.vnnewsvoice.repository;

import com.pmq.vnnewsvoice.pojo.UserProvider;

import java.util.Optional;

public interface UserProviderRepository {
    UserProvider save(UserProvider userProvider);
    UserProvider getUserProviderById(Long id);
    Optional<UserProvider> findByProviderAndProviderType(String providerId, String providerType);
}
