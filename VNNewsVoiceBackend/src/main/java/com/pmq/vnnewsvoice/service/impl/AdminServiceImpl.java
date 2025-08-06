package com.pmq.vnnewsvoice.service.impl;

import com.pmq.vnnewsvoice.pojo.Admin;
import com.pmq.vnnewsvoice.repository.AdminRepository;
import com.pmq.vnnewsvoice.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;


@Service
public class AdminServiceImpl implements AdminService {

    @Autowired
    private AdminRepository adminRepository;

    @Override
    public Admin addAdmin(Admin admin) {
        if(admin == null){
            return null;
        }
        return adminRepository.addAdmin(admin);
    }

    @Override
    public Optional<Admin> getAdminById(Long id) {
        if(id <= 0 || id == null){
            return Optional.empty();
        }
        return adminRepository.getAdminById(id);
    }

    @Override
    public Optional<Admin> getAdminByUserId(Long userId) {
        if(userId <= 0 || userId == null){
            return Optional.empty();
        }
        return getAdminByUserId(userId);
    }

    @Override
    public Admin updateAdmin(Admin admin) {
        if(admin == null) {
            return null;
        }
        return adminRepository.updateAdmin(admin);
    }

    @Override
    public boolean deleteAdmin(Long id) {
        if(id <= 0){
            return false;
        }
        return adminRepository.deleteAdmin(id);
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
