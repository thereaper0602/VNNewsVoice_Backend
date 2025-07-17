package com.pmq.vnnewsvoice.service;

import com.pmq.vnnewsvoice.pojo.Admin;

import java.util.Map;
import java.util.Optional;

public interface AdminService {
    Admin addAdmin(Admin admin);

    Optional<Admin> getAdminById(Long id);

    Admin updateAdmin(Admin admin);

    boolean deleteAdmin(Long id);

    long countAdmins();

    long countSearchAdmins(Map<String, String> filters);
}
