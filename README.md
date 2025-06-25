# 📂 Directory Size Calculator

A Spring Boot-based REST API that simulates a file system with directories and files. It supports commands like `cd`, `ls`, `size`, and more — mimicking terminal-style navigation and providing total size calculations for directories (including subdirectories).

---

## 🚀 Features

- `cd <path>`: Change current directory
- `cd ..`: Move to parent directory
- `ls`: List contents of current directory
- `size`: Recursively calculate size of current directory and all subdirectories
- `current`: View current directory path
- `reset`: Reset to root directory
- 📐 Smart file size formatting (KB, MB, GB)

---

## 🗃️ Tables Schema

- **Directory**
  - `id`: Primary Key
  - `name`: Directory name
  - `parent_id`: Self-referencing parent directory ID (nullable for root)

- **FileItem**
  - `id`: Primary Key
  - `name`: File name
  - `size`: File size in KB
  - `directory_id`: FK to `Directory`

> Sample data is loaded via `data.sql` and uses an in-memory H2 database.

---

## ⚙️ How to Run the Project

### 🧾 Prerequisites

- Java 17+
- Maven (or use the included `mvnw` wrapper)
- Git

### 🪜 Steps (Bash)

1. **Clone the Repository**
   ```bash
   git clone https://github.com/amanboobna/directory-size-calculator.git
   cd directory-size-calculator
   ```

2. **Clean Previous Builds**
   ```bash
   rm -rf target
   ```

3. **Run the Application**
   ```bash
   ./mvnw spring-boot:run
   ```

---

## 🧪 Sample API Requests

Use `curl` or Postman to test:

```bash
curl -X GET  "http://localhost:8080/api/directory/ls"
curl -X GET  "http://localhost:8080/api/directory/current"
curl -X POST "http://localhost:8080/api/directory/reset"
curl -X GET  "http://localhost:8080/api/directory/size"
curl -X POST "http://localhost:8080/api/directory/cd?path={pathName}"
curl -X POST "http://localhost:8080/api/directory/cd?path=.."
```

---

## 👤 Author

**Aman Boobna**  
NYU Computer Science Graduate  
📧 ab10465@nyu.edu  
🔗 [LinkedIn](https://linkedin.com/in/aman-boobna/) | [GitHub](https://github.com/amanboobna)
