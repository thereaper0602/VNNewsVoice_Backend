package com.pmq.vnnewsvoice.repository;

import com.pmq.vnnewsvoice.pojo.Role;

import java.util.List;

public interface UserRoleRepository {
    List<Role> getAllUserRoles();
    Role getUserRoleByName(String name);
    Role getUserRoleById(Long id);
    Role addUserRole(Role newRole);
    Role updateUserRole(Role role);
    boolean deleteUserRole(Long id);
}
