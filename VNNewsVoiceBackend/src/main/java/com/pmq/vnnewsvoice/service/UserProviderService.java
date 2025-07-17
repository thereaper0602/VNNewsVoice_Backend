package com.pmq.vnnewsvoice.service;

import com.pmq.vnnewsvoice.pojo.UserProvider;

public interface UserProviderService {
    UserProvider addUserProvider(UserProvider userProvider);
    UserProvider getUserProviderById(Long id);
}
