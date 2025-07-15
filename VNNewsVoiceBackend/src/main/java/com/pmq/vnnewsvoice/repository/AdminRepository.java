package com.pmq.vnnewsvoice.repository;

import com.pmq.vnnewsvoice.pojo.Admin;

import java.util.Map;
import java.util.Optional;

public interface AdminRepository {
    Admin addAdmin(Admin admin);

    Optional<Admin> getAdminById(Long id);

    Admin updateAdmin(Admin admin);

    boolean deleteAdmin(Long id);

    long countAdmins();

    long countSearchAdmins(Map<String, String> filters);
}
