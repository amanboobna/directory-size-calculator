package com.example.directorysizecalculator.model;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "directory")
public class Directory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    private Directory parent;

    @OneToMany(mappedBy = "parent", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Directory> subdirectories = new ArrayList<>();

    @OneToMany(mappedBy = "directory", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<FileItem> files = new ArrayList<>();

    public Directory() {}

    public Directory(String name, Directory parent) {
        this.name = name;
        this.parent = parent;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public Directory getParent() { return parent; }
    public void setParent(Directory parent) { this.parent = parent; }

    public List<Directory> getSubdirectories() { return subdirectories; }
    public void setSubdirectories(List<Directory> subdirectories) { this.subdirectories = subdirectories; }

    public List<FileItem> getFiles() { return files; }
    public void setFiles(List<FileItem> files) { this.files = files; }

    public void addSubdirectory(Directory subdirectory) {
        subdirectories.add(subdirectory);
        subdirectory.setParent(this);
    }

    public void addFile(FileItem file) {
        files.add(file);
        file.setDirectory(this);
    }

    public String getPath() {
        if (parent == null) {
            return "/" + name;
        }
        return parent.getPath() + "/" + name;
    }
}