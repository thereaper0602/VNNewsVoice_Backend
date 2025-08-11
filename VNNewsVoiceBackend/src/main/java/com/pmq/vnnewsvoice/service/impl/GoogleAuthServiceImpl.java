package com.pmq.vnnewsvoice.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import com.pmq.vnnewsvoice.pojo.Reader;
import com.pmq.vnnewsvoice.pojo.UserInfo;
import com.pmq.vnnewsvoice.pojo.UserProvider;
import com.pmq.vnnewsvoice.repository.UserProviderRepository;
import com.pmq.vnnewsvoice.service.GoogleAuthService;
import com.pmq.vnnewsvoice.service.ReaderService;
import com.pmq.vnnewsvoice.service.UserInfoService;
import com.pmq.vnnewsvoice.service.UserRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@Transactional
public class GoogleAuthServiceImpl implements GoogleAuthService {
    @Value("${GOOGLE_CLIENT_ID}")
    private String googleClientId;

    @Autowired
    private UserInfoService userInfoService;

    @Autowired
    private ReaderService readerService;

    @Autowired
    private UserRoleService userRoleService;

    @Autowired
    private UserProviderRepository userProviderRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public UserInfo verifyGoogleToken(String tokenId) throws Exception {
        GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier
                .Builder(new NetHttpTransport(), new GsonFactory())
                .setAudience(Collections.singletonList(googleClientId))
                .build();
        GoogleIdToken idToken = verifier.verify(tokenId);

        if(idToken == null){
            throw new Exception("Invalid Google ID token");
        }

        GoogleIdToken.Payload payload = idToken.getPayload();

        String googleId = payload.getSubject();
        String email = payload.getEmail();
        String name = (String) payload.get("name");
        String pictureUrl = (String) payload.get("picture");

        Optional<UserProvider> existingProvider = userProviderRepository.findByProviderAndProviderType(googleId, "GOOGLE");

        UserInfo userInfo;

        if(existingProvider.isPresent()){
            userInfo = existingProvider.get().getUserId();
        }
        else{
            Optional<UserInfo> existingUser = userInfoService.getUserByEmail(email);
            if(existingUser.isPresent()){
                userInfo = existingUser.get();
            }
            else{
                userInfo = new UserInfo();
                userInfo.setUsername(email.split("@")[0] + "_" + UUID.randomUUID().toString().substring(0, 8)); // Tạo username từ email
                userInfo.setEmail(email);
                userInfo.setPassword(bCryptPasswordEncoder.encode(UUID.randomUUID().toString())); // Mật khẩu ngẫu nhiên
                userInfo.setAvatarUrl(pictureUrl);
                userInfo.setIsActive(true);
                userInfo.setCreatedAt(new Date());
                userInfo.setRoleId(userRoleService.getUserRoleByName("ROLE_READER"));
                userInfo = userInfoService.addUser(userInfo);

                // Tạo Reader cho người dùng mới
                Reader reader = new Reader();
                reader.setUserId(userInfo);
                readerService.addReader(reader);
            }

            UserProvider userProvider = new UserProvider();
            userProvider.setProviderId(googleId);
            userProvider.setProviderType("GOOGLE");

            // Lưu thông tin bổ sung từ Google
            Map<String, Object> providerData = new HashMap<>();
            providerData.put("email", email);
            providerData.put("name", name);
            providerData.put("picture", pictureUrl);

            String jsonProviderData = objectMapper.writeValueAsString(providerData);
            userProvider.setProviderData(jsonProviderData);

            userProvider.setUserId(userInfo);
            userProvider.setCreatedAt(new Date());
            userProviderRepository.save(userProvider);
        }

        return userInfo;

    }
}
