package com.pmq.vnnewsvoice.service.impl;

import com.pmq.vnnewsvoice.pojo.Admin;
import com.pmq.vnnewsvoice.service.AdminService;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;


@Service
public class AdminServiceImpl implements AdminService {
    @Override
    public Admin addAdmin(Admin admin) {
        return null;
    }

    @Override
    public Optional<Admin> getAdminById(Long id) {
        return Optional.empty();
    }

    @Override
    public Admin updateAdmin(Admin admin) {
        return null;
    }

    @Override
    public boolean deleteAdmin(Long id) {
        return false;
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
