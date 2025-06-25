package com.example.directorysizecalculator.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.directorysizecalculator.model.Directory;

@Repository
public interface DirectoryRepository extends JpaRepository<Directory, Long> {
    
    @Query("SELECT d FROM Directory d WHERE d.parent IS NULL")
    Optional<Directory> findRootDirectory();
    
    @Query("SELECT d FROM Directory d WHERE d.parent.id = :parentId")
    List<Directory> findByParentId(@Param("parentId") Long parentId);
    
    @Query("SELECT d FROM Directory d WHERE d.name = :name AND d.parent.id = :parentId")
    Optional<Directory> findByNameAndParentId(@Param("name") String name, @Param("parentId") Long parentId);
    
    @Query("SELECT d FROM Directory d WHERE d.name = :name AND d.parent IS NULL")
    Optional<Directory> findRootByName(@Param("name") String name);
}