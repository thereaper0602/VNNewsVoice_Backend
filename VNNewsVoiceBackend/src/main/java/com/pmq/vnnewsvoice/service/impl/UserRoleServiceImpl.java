package com.pmq.vnnewsvoice.service.impl;

import com.pmq.vnnewsvoice.pojo.Role;
import com.pmq.vnnewsvoice.repository.UserRoleRepository;
import com.pmq.vnnewsvoice.service.UserRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class UserRoleServiceImpl implements UserRoleService {
    @Autowired
    private UserRoleRepository userRoleRepository;

    @Override
    @Transactional(readOnly = true)
    public List<Role> getAllUserRoles() {
        return userRoleRepository.getAllUserRoles();
    }

    @Override
    public Role getUserRoleByName(String name) {
        if(name == null || name.trim().isEmpty()){
            throw new IllegalArgumentException("Role name cannot be empty");
        }
        return userRoleRepository.getUserRoleByName(name);
    }

    @Override
    public Role getUserRoleById(Long id) {
        if(id == null || id <= 0){
            throw new IllegalArgumentException("User Role Id cannot be null or negative");
        }
        return userRoleRepository.getUserRoleById(id);
    }

    @Override
    public Role addUserRole(Role newRole) {
        if(!isValidRole(newRole)){
            throw new IllegalArgumentException("Invalid user role data");
        }

        if(userRoleRepository.getUserRoleByName(newRole.getName()) != null){
            throw new IllegalArgumentException("Role name already exists");
        }

        return userRoleRepository.addUserRole(newRole);
    }

    @Override
    public Role updateUserRole(Role role) {
        if(role.getId() == null || userRoleRepository.getUserRoleById(role.getId()) == null){
            throw new IllegalArgumentException("User Role not found");
        }
        return userRoleRepository.updateUserRole(role);
    }

    @Override
    public boolean deleteUserRole(Long id) {
        if(id == null || id <= 0){
            throw new IllegalArgumentException("User role id cannot be null or negative");
        }

        Role existingRole = userRoleRepository.getUserRoleById(id);
        if(existingRole == null){
            throw new IllegalArgumentException("User Role not Found");
        }

        if(existingRole.getUserinfoSet() != null || !existingRole.getUserinfoSet().isEmpty()){
            throw new IllegalArgumentException("Cannot delete role with associated users");
        }

        return userRoleRepository.deleteUserRole(id);
    }

    private boolean isValidRole(Role role){
        if(role == null || role.getName() == null || role.getName().trim().isEmpty()){
            return false;
        }

        return true;
    }

}
