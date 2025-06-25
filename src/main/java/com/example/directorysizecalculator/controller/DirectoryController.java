package com.example.directorysizecalculator.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.directorysizecalculator.service.DirectoryService;

@RestController
@RequestMapping("/api/directory")
public class DirectoryController {

    @Autowired
    private DirectoryService directoryService;

    @GetMapping("/current")
    public String getCurrentDirectory() {
        return "Current directory: " + directoryService.getCurrentDirectory().getPath();
    }

    @PostMapping("/cd")
    public String changeDirectory(@RequestParam String path) {
        return directoryService.changeDirectory(path);
    }

    @GetMapping("/ls")
    public String listDirectory() {
        return directoryService.listDirectory();
    }

    @GetMapping("/size")
    public String getDirectorySize() {
        return directoryService.calculateSize();
    }

    @PostMapping("/reset")
    public String resetToRoot() {
        directoryService.resetToRoot();
        return "Reset to root: " + directoryService.getCurrentDirectory().getPath();
    }

    // CLI-style endpoint to simulate basic shell commands
    @PostMapping("/command")
    public String executeCommand(@RequestParam String command,
                                 @RequestParam(required = false) String argument) {
        switch (command.trim().toLowerCase()) {
            case "pwd":
                return getCurrentDirectory();
            case "cd":
                if (argument == null || argument.isBlank()) {
                    return "Usage: cd <directory_name>";
                }
                return changeDirectory(argument);
            case "ls":
                return listDirectory();
            case "size":
                return getDirectorySize();
            case "reset":
                return resetToRoot();
            default:
                return "Unknown command: " + command + ". Try: pwd, cd, ls, size, reset.";
        }
    }
}
