package com.pmq.vnnewsvoice.service.impl;

import com.pmq.vnnewsvoice.helpers.CloudinaryHelper;
import com.pmq.vnnewsvoice.pojo.UserInfo;
import com.pmq.vnnewsvoice.repository.UserInfoRepository;
import com.pmq.vnnewsvoice.service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

@Service
@Transactional
public class UserInfoServiceImpl implements UserInfoService {

    private static final Logger LOGGER = Logger.getLogger(UserInfoServiceImpl.class.getName());

    @Autowired
    private UserInfoRepository userInfoRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private CloudinaryHelper cloudinaryHelper;

    @Override
    public UserInfo addUser(UserInfo userinfo) throws IOException {
        if (userinfo == null ||
                userinfo.getUsername().isEmpty() ||
                userinfo.getPassword().isEmpty()) {
            return null;
        }
        // isActive = true by default
        userinfo.setIsActive(true);
        userinfo.setPassword(passwordEncoder.encode(userinfo.getPassword()));
        // Xử lý upload cloudinary ở đây
        MultipartFile avatarFile = userinfo.getAvatarFile();
        if (avatarFile != null) {
            Map<String, String> res = cloudinaryHelper.uploadMultipartFile(userinfo.getAvatarFile());
            userinfo.setAvatarUrl((String) res.get("url"));
            userinfo.setAvatarPublicId((String) res.get("publicId"));
        }

        return userInfoRepository.addUser(userinfo);
    }

    // Thêm phương thức process avatar ở đây
    private void processUserAvatar(UserInfo user, UserInfo existingUser) {
        // If the avatar file is null, keep the existing avatar
        if (user.getAvatarFile() == null || user.getAvatarFile().isEmpty()) {
            user.setAvatarUrl(existingUser.getAvatarUrl());
            user.setAvatarPublicId(existingUser.getAvatarPublicId());
        } else {
            // Handle avatar upload with Cloudinary - this would be implemented in a
            // separate method
            try {

                Map res = cloudinaryHelper.uploadMultipartFile(user.getAvatarFile());
                user.setAvatarUrl((String) res.get("url"));
                user.setAvatarPublicId((String) res.get("publicId"));

            } catch (IOException e) {
                LOGGER.log(Level.SEVERE, "Error uploading avatar for user ID: " + user.getId(), e);
                // Keep existing avatar on error
                user.setAvatarUrl(existingUser.getAvatarUrl());
                user.setAvatarPublicId(existingUser.getAvatarPublicId());
            }
        }
    }

    @Override
    public Optional<UserInfo> getUserById(Long id) {
        if (id <= 0 || id == null) {
            return Optional.of(null);
        }
        return userInfoRepository.getUserById(id);
    }

    @Override
    public Optional<UserInfo> getUserByUsername(String username) {
        if (username.isEmpty()) {
            return Optional.of(null);
        }
        return userInfoRepository.getUserByUsername(username);
    }

    @Override
    public Optional<UserInfo> getUserByEmail(String email) {
        if(email.isEmpty() || email == null){
            return Optional.empty();
        }
        return userInfoRepository.getUserByEmail(email);
    }

    @Override
    public List<UserInfo> getUsers(Map<String, String> params) {
        return userInfoRepository.getUsers(params);
    }

    @Override
    public List<UserInfo> searchUsers(Map<String, String> filters, Map<String, String> params) {
        return userInfoRepository.searchUsers(filters, params);
    }

    @Override
    public boolean updateUser(UserInfo userinfo) throws Exception {
        Optional<UserInfo> userInfoOpt = getUserById(userinfo.getId());
        if (userInfoOpt.isPresent()) {
            UserInfo existingUser = userInfoOpt.get();

            if (userinfo.getPassword() != null
                    && !userinfo.getPassword().isEmpty()
                    && !passwordEncoder.matches(userinfo.getPassword(), existingUser.getPassword())
                    && !existingUser.getPassword().equals(userinfo.getPassword())) {
                userinfo.setPassword(passwordEncoder.encode(userinfo.getPassword()));
            } else {
                // Nếu password là null hoặc rỗng, sử dụng password hiện tại
                userinfo.setPassword(existingUser.getPassword());
            }
            processUserAvatar(userinfo, existingUser);
            return userInfoRepository.updateUser(userinfo);
        } else {
            throw new Exception("Lỗi khi cập nhật người dùng");
        }
    }

    @Override
    public boolean deleteUser(Long id) throws Exception {
        Optional<UserInfo> userOpt = getUserById(id);

        if (userOpt.isPresent()) {
            UserInfo user = userOpt.get();
            if (user.getAvatarPublicId() != null && !user.getAvatarPublicId().isEmpty()) {
                try {
                    cloudinaryHelper.deleteFile(user.getAvatarPublicId());
                } catch (IOException e) {
                    LOGGER.log(Level.SEVERE, "Error deleting avatar for user ID: " + id, e);
                }
            }
            return userInfoRepository.deleteUser(id);
        } else {
            throw new Exception("Lỗi xóa người dùng");
        }
    }
    
    @Override
    public boolean toggleUserActive(Long id) throws Exception {
        Optional<UserInfo> userOpt = getUserById(id);
        
        if (userOpt.isPresent()) {
            UserInfo user = userOpt.get();
            // Đảo ngược trạng thái isActive
            user.setIsActive(!user.getIsActive());
            return userInfoRepository.updateUser(user);
        } else {
            throw new Exception("Không tìm thấy người dùng");
        }
    }

    @Override
    public long countUsers(Map<String, String> params) {
        return userInfoRepository.countUsers(params);
    }

    @Override
    public long countSearchUsers(Map<String, String> filters, Map<String, String> params) {
        return userInfoRepository.countSearchUsers(filters, params);
    }
}
