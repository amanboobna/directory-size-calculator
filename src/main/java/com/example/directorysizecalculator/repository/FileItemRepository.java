package com.example.directorysizecalculator.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.directorysizecalculator.model.FileItem;

@Repository
public interface FileItemRepository extends JpaRepository<FileItem, Long> {
    
    @Query("SELECT f FROM FileItem f WHERE f.directory.id = :directoryId")
    List<FileItem> findByDirectoryId(@Param("directoryId") Long directoryId);
    
    @Query("SELECT SUM(f.size) FROM FileItem f WHERE f.directory.id = :directoryId")
    Long sumSizeByDirectoryId(@Param("directoryId") Long directoryId);
}