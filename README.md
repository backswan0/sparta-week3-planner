# 🗓️ Creating a Planner Application Using Spring Boot

## 💻 Introduction
- This project is an assignment designed to evaluate students' understanding of the online lecture.
- Each application is developed as a personal project.
- All applications are designed with console-based user interfaces.
  
## 📆 Development Period
- **Study**: 29/11/2024 – 03/12/2024
- **Development**: 03/12/2024 – 10/12/2024

## 🛠️ Tech Stack
- Java 17
- Spring Boot 3.4.0
- MySQL 9.1.0
- JdbcTemplate

## 🔗 ERD

```mermaid
erDiagram
    PLANS {
        id bigint PK
        name varchar
        password varchar
        plannedDate date
        title varchar
        task varchar
        createdDateTime datetime
        updatedDateTime datetime
    }
```

### ERD Note
- The SQL database table name of `PLANS` is `planner`.

## 📜 API Specification 
### Basic Information 
- Base URL: /plans
- Response Format: JSON
- Character Encoding: UTF-8

### API List
| Method | URI                    | Description             | Request Parameters                             | Response Code |
|--------|------------------------|-------------------------|------------------------------------------------|---------------|
| POST   | /plans                 | Create plan             |                                                | 201           |
| GET    | /plans                 | Read all plans          | name, updatedDate                              | 200           |
| GET    | /plans/{id}            | Read specific plan      | id                                             | 200           |
| PATCH  | /plans/{id}            | Update plan partially   | id, name, password, plannedDate, title, task   | 200           |
| DELETE | /plans/{id}            | Delete plan             | id, password                                   | 200           |

### API Details
#### Request Body Details
1. **`POST` Create Plan**
    ```json
    {
        "name" : "사용자명",
        "password" : "비밀번호",
        "plannedDate" : "일정 날짜",
        "title": "일정 제목",
        "task": "일정 내용"
    }
    ```

2. **`PATCH` Update Plan**
    ```json
    {
        "name" : "사용자명",
        "password" : "비밀번호",
        "plannedDate" : "일정 날짜",
        "title": "일정 제목",
        "task": "일정 내용"
    }
    ```
 
3. **`DELETE` Delete Plan**
    ```json
    {
        "password" : "비밀번호"
    }
    ```

#### Response Body Details
1. **`GET` Read All Plans**
    ```json
[
    {
        "id": 1,
        "name": "사용자명",
        "plannedDate": "일정 날짜",
        "title": "일정 제목",
        "task": "일정 내용",
        "createdDateTime": "2024-12-10T13:49:42",
        "updatedDateTime": "2024-12-10T13:49:42"
    },
    {
        "id": 2,
        "name": "사용자명2",
        "plannedDate": "일정 날짜2",
        "title": "일정 제목2",
        "task": "일정 내용2",
        "createdDateTime": "2024-12-10T14:00:00",
        "updatedDateTime": "2024-12-10T14:00:00"
    }
]
    ```

2. **`GET` Read Specific Plan**
    ```json
    {
        "id": 1,
        "name": "사용자명",
        "plannedDate": "일정 날짜",
        "title": "일정 제목",
        "task": "일정 내용",
        "createdDateTime": "2024-12-10T13:49:42",
        "updatedDateTime": "2024-12-10T13:49:42"
    }
    ```

## 🚀 Level

### 1️⃣ 
- **Characteristics**
  - 
  - 
- **Features** 
  - 
  - 
  - 
- **Usage**: 

## 📜 More Information
- [Visit Development Journal](https://writingforever162.tistory.com)
- [Visit Troubleshooting Records](https://writingforever162.tistory.com/category/Troubleshooting%3A%20%EB%AC%B4%EC%97%87%EC%9D%B4%20%EB%AC%B8%EC%A0%9C%EC%98%80%EB%8A%94%EA%B0%80%3F)
