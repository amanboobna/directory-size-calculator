-- Root directory
INSERT INTO directory (id, name, parent_id) VALUES (1, 'root', NULL);

-- Subdirectories
INSERT INTO directory (id, name, parent_id) VALUES (2, 'documents', 1);
INSERT INTO directory (id, name, parent_id) VALUES (3, 'pictures', 1);
INSERT INTO directory (id, name, parent_id) VALUES (4, 'work', 2);
INSERT INTO directory (id, name, parent_id) VALUES (5, 'personal', 2);
INSERT INTO directory (id, name, parent_id) VALUES (6, 'vacation', 3);

-- Files
INSERT INTO file_item (id, name, size, directory_id) VALUES (1, 'readme.txt', 10, 1);
INSERT INTO file_item (id, name, size, directory_id) VALUES (2, 'doc1.pdf', 50, 2);
INSERT INTO file_item (id, name, size, directory_id) VALUES (3, 'doc2.pdf', 75, 2);
INSERT INTO file_item (id, name, size, directory_id) VALUES (4, 'report.docx', 120, 4);
INSERT INTO file_item (id, name, size, directory_id) VALUES (5, 'presentation.pptx', 250, 4);
INSERT INTO file_item (id, name, size, directory_id) VALUES (6, 'photo1.jpg', 200, 3);
INSERT INTO file_item (id, name, size, directory_id) VALUES (7, 'photo2.jpg', 150, 3);
INSERT INTO file_item (id, name, size, directory_id) VALUES (8, 'beach.jpg', 300, 6);
INSERT INTO file_item (id, name, size, directory_id) VALUES (9, 'mountain.jpg', 280, 6);
INSERT INTO file_item (id, name, size, directory_id) VALUES (10, 'diary.txt', 1500, 5);