package com.stc.system.management.repo;

import com.stc.system.management.entity.Permission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PermissionRepo extends JpaRepository<Permission, Long> {
    List<Permission> findByPermissionGroupId(Long permissionGroupId);
}
