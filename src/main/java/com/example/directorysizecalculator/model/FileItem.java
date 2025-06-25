package com.example.directorysizecalculator.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "file_item")
public class FileItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private long size; 

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "directory_id", nullable = false)
    private Directory directory;

    public FileItem() {}

    public FileItem(String name, long size, Directory directory) {
        this.name = name;
        this.size = size;
        this.directory = directory;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public long getSize() { return size; }
    public void setSize(long size) { this.size = size; }

    public Directory getDirectory() { return directory; }
    public void setDirectory(Directory directory) { this.directory = directory; }

    //added formatting which automatically converts size to MB or GB
    public String getFormattedSize() {
        if (size < 1024) return size + " KB";
        if (size < 1024 * 1024) return String.format("%.1f MB", size / 1024.0);
        return String.format("%.1f GB", size / (1024.0 * 1024.0));
    }
}