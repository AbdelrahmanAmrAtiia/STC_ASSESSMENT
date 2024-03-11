package com.stc.system.management.repo;

import com.stc.system.management.entity.PermissionGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PermissionGroupRepo extends JpaRepository<PermissionGroup, Long> {
}
