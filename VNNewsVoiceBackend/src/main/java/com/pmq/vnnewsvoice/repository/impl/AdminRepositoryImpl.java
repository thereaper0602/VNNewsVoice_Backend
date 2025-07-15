package com.pmq.vnnewsvoice.repository.impl;

import com.pmq.vnnewsvoice.pojo.Admin;
import com.pmq.vnnewsvoice.repository.AdminRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;
import java.util.Optional;

@Repository
@Transactional
public class AdminRepositoryImpl implements AdminRepository {
    @Override
    public Admin addAdmin(Admin admin) {
        return null;
    }

    @Override
    public Optional<Admin> getAdminById(Long id) {
        return null;
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
