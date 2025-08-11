package com.pmq.vnnewsvoice.service;

import com.pmq.vnnewsvoice.pojo.UserInfo;

public interface GoogleAuthService {
    UserInfo verifyGoogleToken(String tokenId) throws Exception;
}
