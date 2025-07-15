package com.pmq.vnnewsvoice.repository;

import com.pmq.vnnewsvoice.pojo.UserProvider;

public interface UserProviderRepository {
    UserProvider addUserProvider(UserProvider userProvider);
    UserProvider getUserProviderById(Long id);
}
