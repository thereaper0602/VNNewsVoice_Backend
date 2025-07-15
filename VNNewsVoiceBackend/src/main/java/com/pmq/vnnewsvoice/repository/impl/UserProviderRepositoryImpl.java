package com.pmq.vnnewsvoice.repository.impl;

import com.pmq.vnnewsvoice.pojo.UserProvider;
import com.pmq.vnnewsvoice.repository.UserProviderRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public class UserProviderRepositoryImpl implements UserProviderRepository {
    @Override
    public UserProvider addUserProvider(UserProvider userProvider) {
        return null;
    }

    @Override
    public UserProvider getUserProviderById(Long id) {
        return null;
    }
}
