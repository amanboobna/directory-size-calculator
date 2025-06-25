package com.example.directorysizecalculator.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.directorysizecalculator.model.Directory;
import com.example.directorysizecalculator.model.FileItem;
import com.example.directorysizecalculator.repository.DirectoryRepository;
import com.example.directorysizecalculator.repository.FileItemRepository;

@Service
@Transactional
public class DirectoryService {

    @Autowired
    private DirectoryRepository directoryRepository;

    @Autowired
    private FileItemRepository fileItemRepository;

    private Directory currentDirectory;

    public DirectoryService() {}

    public Directory getCurrentDirectory() {
        if (currentDirectory == null) {
            currentDirectory = directoryRepository.findRootDirectory()
                .orElseThrow(() -> new RuntimeException("No root directory found"));
        }
        return currentDirectory;
    }

    // cd command implementation
    public String changeDirectory(String path) {
        try {
            Directory targetDirectory;
            
            if (path.equals("/") || path.equals("root")) {
                targetDirectory = directoryRepository.findRootDirectory()
                    .orElseThrow(() -> new RuntimeException("Root directory not found"));
            } else if (path.equals("..")) {
                Directory current = getCurrentDirectory();
                if (current.getParent() != null) {
                    targetDirectory = current.getParent();
                } else {
                    return "Already at root directory";
                }
            } else {
                // Find subdirectory by name
                Directory current = getCurrentDirectory();
                targetDirectory = current.getSubdirectories().stream()
                    .filter(dir -> dir.getName().equals(path))
                    .findFirst()
                    .orElseThrow(() -> new RuntimeException("Directory '" + path + "' not found"));
            }
            
            currentDirectory = targetDirectory;
            return "Changed to directory: " + currentDirectory.getPath();
        } catch (Exception e) {
            return "Error: " + e.getMessage();
        }
    }

    // ls command implementation
    public String listDirectory() {
        try {
            Directory current = getCurrentDirectory();
            StringBuilder result = new StringBuilder();
            
            result.append("Contents of ").append(current.getPath()).append(":\n");
            
            // List subdirectories
            if (!current.getSubdirectories().isEmpty()) {
                result.append("Directories:\n");
                for (Directory subdir : current.getSubdirectories()) {
                    long size = calculateDirectorySize(subdir.getId());
                    result.append("  ").append(subdir.getName())
                          .append(" (").append(formatSize(size)).append(")\n");
                }
            }
            
            // List files
            if (!current.getFiles().isEmpty()) {
                result.append("Files:\n");
                for (FileItem file : current.getFiles()) {
                    result.append("  ").append(file.getName())
                          .append(" (").append(file.getFormattedSize()).append(")\n");
                }
            }
            
            if (current.getSubdirectories().isEmpty() && current.getFiles().isEmpty()) {
                result.append("Directory is empty\n");
            }
            
            return result.toString();
        } catch (Exception e) {
            return "Error listing directory: " + e.getMessage();
        }
    }

    public String calculateSize() {
        try {
            Directory current = getCurrentDirectory();
            long totalSize = calculateDirectorySize(current.getId());
            
            return String.format("Total size of '%s': %s (%d KB)", 
                current.getPath(), formatSize(totalSize), totalSize);
        } catch (Exception e) {
            return "Error calculating size: " + e.getMessage();
        }
    }

    public long calculateDirectorySize(Long directoryId) {
        Directory directory = directoryRepository.findById(directoryId)
            .orElseThrow(() -> new RuntimeException("Directory not found"));
        
        return calculateSizeRecursive(directory);
    }

    private long calculateSizeRecursive(Directory directory) {
        long totalSize = 0;
        
        for (FileItem file : directory.getFiles()) {
            totalSize += file.getSize();
        }
        
        // recursively adds sizes of all subdirectories
        for (Directory subdir : directory.getSubdirectories()) {
            totalSize += calculateSizeRecursive(subdir);
        }
        
        return totalSize;
    }

    //added formatting which automatically converts size to MB or GB
    private String formatSize(long sizeInKB) {
        if (sizeInKB < 1024) return sizeInKB + " KB";
        if (sizeInKB < 1024 * 1024) return String.format("%.1f MB", sizeInKB / 1024.0);
        return String.format("%.1f GB", sizeInKB / (1024.0 * 1024.0));
    }

    public List<Directory> getAllDirectories() {
        return directoryRepository.findAll();
    }

    public List<FileItem> getAllFiles() {
        return fileItemRepository.findAll();
    }

    public void resetToRoot() {
        currentDirectory = directoryRepository.findRootDirectory()
            .orElseThrow(() -> new RuntimeException("No root directory found"));
    }
}