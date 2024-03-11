package com.stc.system.management.repo;

import com.stc.system.management.entity.File;
import com.stc.system.management.projection.FileMetadataProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface FileRepo extends JpaRepository<File, Long> {

    @Query(value = "SELECT f.id as id, f.file_binary as fileBinary, p.level as level " +
            "FROM file f " +
            "JOIN item i ON f.item_id = i.id " +
            "JOIN permission_group pg ON i.permission_group_id = pg.id " +
            "JOIN permission p ON pg.id = p.id " +
            "WHERE f.id = ?1", nativeQuery = true)
    FileMetadataProjection getFileMetadata(Long fileId);
}
