# 🐴 Donkey Project 🐴
<img src="https://img.shields.io/badge/springboot-6DB33F?style=for-the-badge&logo=springboot&logoColor=white"> <img src="https://img.shields.io/badge/java-007396?style=for-the-badge&logo=java&logoColor=white"> <img src="https://img.shields.io/badge/react-61DAFB?style=for-the-badge&logo=react&logoColor=black"> <img src="https://img.shields.io/badge/PostgreSQL-4169E1?style=for-the-badge&logo=postgresql&logoColor=white"> <img src="https://img.shields.io/badge/gradle-02303A?style=for-the-badge&logo=gradle&logoColor=white"> <img src="https://img.shields.io/badge/AWS-232F3E?style=for-the-badge&logo=AmazonAWS&logoColor=white"> <img src="https://img.shields.io/badge/github-181717?style=for-the-badge&logo=github&logoColor=white">



> 취업과 회사에 대해 자유롭게 이야기 할 수 있는 커뮤니티 

![img.png](readme/mainImg.png)

## 📖Description

- 누구나 쉽게 접근할 수 있는 사이트
- 자유롭게 표현할 수 있는 사이트
- 취업 정보를 제약없이 교류할 수 있는 사이트

취업 준비에 필요한 자료를 구할 수 있는 곳이 흔치 않고, 결제를 해야 자료를 열람할 수 있는 곳이 많습니다.

편하게 취준생들이 편하게 모여 이야기 하고, 취업에 성공한 선배님들과 대화하며, 누구에게나 열려있는 커뮤니티로 좋은 추억이 되었으면 좋겠습니다.



### 1. 💾**개발 환경**
#### 리펙토링 전
![img.png](readme/DevelopmentEnvironment.png)

#### MSA 리펙토링 후
![img.png](readme/refactoring_skill.png)

- Java JDK 21, JavaScript
- 프론트엔드 : React, HTML, tailwind, Vite
- 백엔드 : Spring Boot, Spring Security, Spring Cloud
- 데이터베이스 : PostgreSQL, Redis
- ORM : JPA
- 배포환경 : AWS EC2, RDS, S3, CodeDeploy, GitHubActions, CloudFront, Docker, ECR
- 협업도구 : GitHub, Notion, Miro, ERD Cloud, Figma

## 🏭System Structure
#### 리펙토링 전
![img.png](readme/SystemStructure.png)
#### MSA 리펙토링 후
![img.png](readme/refactoring_structure.png)

## 🔐ERD Structure
![img.png](readme/ERD.png)


### 2. 🤔**기능 정의서**

- 초안

![img.png](readme/mindmap.png)

- 완료

![img.png](readme/FeatureSpecification.png)

### 3. 🔎**개발 일정**

![img.png](readme/DevelopmentSchedule.png)

## ✨UI(화면) 설계서 

- [피그마 링크 바로가기]([https://www.figma.com/file/UxckBh583uPenF9pmlpJUd/Untitled?type=design&node-id=0%3A1&mode=design&t=xacdis5Qkeh8AcrZ-1])

|                                                                                                                       |                                                                                                                   |
|-----------------------------------------------------------------------------------------------------------------------|-------------------------------------------------------------------------------------------------------------------|
| Main Page (Member)                                                                                                    | Main Page (Guest)                                                                                                 |
| <img src="https://github.com/lth01/ormi-community/assets/139758405/2eb7e023-322b-42f2-a676-60a5603d1766" width="370"> | <img src="https://github.com/lth01/ormi-community/assets/139758405/c1397cc9-51d8-46fe-8133-6467f75a85f0" width="370"> |
| Login Page                                                                                                            | SignUp Page                                                                                                       |
| <img src="https://github.com/lth01/ormi-community/assets/139758405/91d139f6-fa1e-4a8e-af42-1d3eed0fe794" width="370"> | <img src="https://github.com/lth01/ormi-community/assets/139758405/457792b5-48d8-424c-92fb-bd79caeba71e" width="370">    |
| SignUp complete Page                                                                                                  | Modify-info Page                                                                                                  |
| <img src="https://github.com/lth01/ormi-community/assets/139758405/962ca99e-8d30-4508-b3ec-edca7d906694" width="370"> | <img src="https://github.com/lth01/ormi-community/assets/139758405/2d3fb2b9-c49c-4f65-a0fd-ae020204cb01" width="370">    |
| Find Password Page                                                                                                    | Change Password Page                                                                                              |
| <img src="https://github.com/lth01/ormi-community/assets/139758405/179ce85e-6933-4474-b8a9-951149c3292c" width="370"> | <img src="https://github.com/lth01/ormi-community/assets/139758405/96eb0e20-a95c-4ce2-b990-7abd2f56e05c" width="370">    |
| Board Write Page                                                                                                      | Admin Page                                                                                                        |
| <img src="https://github.com/lth01/ormi-community/assets/139758405/18ac552c-7592-4eaf-993e-8e43cc11f01e" width="370"> | <img src="https://github.com/lth01/ormi-community/assets/139758405/4e27ac65-c996-43fe-9ecf-b38799f0f5b7" width="370">    |
## 📂Project Structure

### 🌐 Front-End
```
📁 src
├── 📁 assets
├── 📁 components
│   ├── 📁 Board
│   ├── 📁 Comment
│   ├── 📁 Document
│   ├── 📁 Icon
│   ├── 📁 Industry
│   ├── 📁 Layout
│   ├── 📁 Menu
│   ├── 📁 Password
│   └── 📁 ui
├── 📁 lib
├── 📁 routes
│   ├── 📁 Board
│   ├── 📁 Document
│   ├── 📁 Main
│   ├── 📁 Password
│   ├── 📁 Signup
│   ├── 📁 User
│   └── 📁 admin
└── 📁 utils
```
### ⚙️ Back-End
#### 리펙토링 전
```
📁 src
├── 📁 config
├── 📁 controller
├── 📁 domain
│   ├── 📁 dto
│   └── 📁 entity
├── 📁 repository
├── 📁 security
│   ├── 📃 MemberDetailsService.java
│   ├── 📁 exception
│   ├── 📁 filter
│   └── 📁 handler
└── 📁 service
```
#### MSA 리펙토링 후
```java
🧩 donkey-community
├─ 🐘 backend
│  ├─ 🔑 config-server
│  │  └─ 📁src.main.java.com.community.config
│  ├─ 📄 document
│  │  └─ 📁src.main.java.com.community.document
│  │      ├─ 📁 admin
│  │      │  ├─ 📁controller
│  │      │  ├─ 📁domain
│  │      │  │  └─ 📁 dto
│  │      │  │  └─ 📁 entity
│  │      │  ├─ 📁repository
│  │      │  └─ 📁service
│  │      ├─ 📁 document
│  │      │  ├─ 📁controller
│  │      │  ├─ 📁domain
│  │      │  │  └─ 📁 dto
│  │      │  │  └─ 📁 entity
│  │      │  ├─ 📁repository
│  │      │  └─ 📁service
│  │      └─ 📃 DocumentApplication.java
│  ├─ 🚀 donkey-service
│  │  └─ 📁src.main.java.com.community
│  │      ├─ 📁 config
│  │      ├─ 📁 controller
│  │      ├─ 📁 domain
│  │      │  └─ 📁 dto
│  │      │  └─ 📁 entity
│  │      ├─ 📁 repository
│  │      ├─ 📁 security
│  │      │  └─ 📁 filter
│  │      │  └─ 📁 handler
│  │      └─ 📁 service
│  │      └─ 📃 CommunityApplication.java
│  ├─ 🌐 eureka-server
│  │  └─ 📁src.main.java.com.community.eureka
│  │      └─ 📃 EurekaServerApplication.java
│  ├─ 🌉 gateway-server
│  │  └─ 📁src.main.java.com.community.gateway
│  │      ├─ 📁 filter
│  │      ├─ 📁 global
│  │      │  └─ 📁 config
│  │      │  └─ 📁 dto
│  │      │  └─ 📁 exception
│  │      │  └─ 📁 util
│  │      └─ 📃 GatewayServerApplication.java
│  └─ 👥 member
│     └─ 📁src.main.java.com.community.member
│        ├─ 📁 controller
│        ├─ 📁 domain
│        │  └─ 📁 dto
│        │  └─ 📁 entity
│        ├─ 📁 repository
│        ├─ 📁 security
│        │  └─ 📁 filter
│        │  └─ 📁 handler
│        └─ 📁 service
│        └─ 📃 MemberApplication.java
└─ 🐳 docker
   └─ 📃 docker-compose.yml
```

## 🎈API 명세서

### 📁 Member
| 🏷NAME         | ⚙METHOD | 📎URL                         | 📖DESCRIPTION            |
|---------------------|---------|-------------------------------|--------------------------|
| withdrawal     | PUT     | /member/withdrawal            | 회원 탈퇴 요청           |
| modify         | PUT     | /member/modifyInfo            | 회원 정보 수정           |
| signup         | POST    | /member/register              | 회원 가입                |
| findPassword   | POST    | /member/findpassword          | 비밀번호 찾기            |
| changePassword | POST    | /member/changepassword        | 비밀번호 변경            |
| userInfo       | GET     | /member/userinfo              | 사용자 정보 조회         |
| userInfo_1     | GET     | /member/userinfo/{email}      | 이메일로 사용자 정보 조회|

### 📁 Document
| 🏷NAME              | ⚙METHOD | 📎URL                         | 📖DESCRIPTION |
|---------------------|---------|-------------------------------|---------------|
| increaseDocumentLike| PUT     | /document/{document_id}/like  | 게시글 좋아요 증가    |
| modifyDocument      | PUT     | /document/manage/{document_id}| 게시글 수정         |
| deleteDocument      | DELETE  | /document/manage/{document_id}| 게시글 삭제         |
| saveDocument        | POST    | /document/manage              | 게시글 저장         |
| showOneDocument     | GET     | /document/{document_id}       | 특정 게시글 조회      |
| showAllDocument     | GET     | /document/list/{board_id}     | 게시판별 모든 게시글 조회 |

### 📁 Comment
| 🏷NAME              | ⚙METHOD | 📎URL                         | 📖DESCRIPTION            |
|---------------------|---------|-------------------------------|--------------------------|
| showOneComment      | GET     | /comment/{comment_id}         | 특정 댓글 조회           |
| modifyComment       | PUT     | /comment/{comment_id}         | 댓글 수정                |
| deleteComment       | DELETE  | /comment/{comment_id}         | 댓글 삭제                |
| likeComment         | PUT     | /comment/{comment_id}/like    | 댓글 좋아요 처리         |
| saveComment         | POST    | /comment/{doc_id}             | 댓글 저장                |
| showCommentAll      | GET     | /comment/list/{doc_id}        | 게시글 ID로 모든 댓글 조회 |

### 📁 Viewership
| 🏷NAME              | ⚙METHOD | 📎URL                         | 📖DESCRIPTION            |
|---------------------|---------|-------------------------------|--------------------------|
| searchViewershipCount| GET    | /viewership/{doc_id}          | 게시글 조회수 조회         |
| updateViewershipCount| PUT    | /viewership/{doc_id}          | 게시글 조회수 업데이트     |

### 📁 Like
| 🏷NAME              | ⚙METHOD | 📎URL                         | 📖DESCRIPTION            |
|---------------------|---------|-------------------------------|--------------------------|
| searchLikeItCount   | GET     | /likeit/{uuid}                | 좋아요 수 조회           |
| updateLikeItCount   | PUT     | /likeit/{uuid}                | 좋아요 수 업데이트       |

### 📁 Report
| 🏷NAME              | ⚙METHOD | 📎URL                         | 📖DESCRIPTION            |
|---------------------|---------|-------------------------------|--------------------------|
| acceptReport        | PUT     | /admin/report/accept/{report_id}| 신고 승인 처리        |
| reportDocument      | POST    | /report/doc/{document_id}     | 게시글 신고                |
| reportComment       | POST    | /report/com/{comment_id}      | 댓글 신고                |
| showAllReport       | GET     | /admin/report                 | 모든 신고 내역 조회      |

### 📁 Admin
| 🏷NAME              | ⚙METHOD | 📎URL                         | 📖DESCRIPTION            |
|---------------------|---------|-------------------------------|--------------------------|
| updateBoardStatus   | PUT     | /admin/board                  | 게시판 상태 업데이트     |
| savePasswordQuestion| POST    | /admin/passwordquestion       | 비밀번호 질문 저장       |
| saveIndustry        | POST    | /admin/industry               | 산업 정보 저장           |

### 📁 Password Question
| 🏷NAME              | ⚙METHOD | 📎URL                         | 📖DESCRIPTION            |
|---------------------|---------|-------------------------------|--------------------------|
| searchPasswordQuestion| GET   | /passwordquestion             | 비밀번호 질문 조회       |

### 📁 Industry
| 🏷NAME              | ⚙METHOD | 📎URL                         | 📖DESCRIPTION            |
|---------------------|---------|-------------------------------|--------------------------|
| searchIndustry      | GET     | /industry                     | 산업 정보 조회           |

### 📁 Company
| 🏷NAME              | ⚙METHOD | 📎URL                         | 📖DESCRIPTION            |
|---------------------|---------|-------------------------------|--------------------------|
| bringCompanyDataById| GET     | /companydata/{com_id}         | 회사 ID로 회사 데이터 조회|
| bringCompanyDataByName| GET   | /companydata/name/{com_Name}  | 회사 이름으로 회사 데이터 조회|

### 📁 Board
| 🏷NAME              | ⚙METHOD | 📎URL                         | 📖DESCRIPTION            |
|---------------------|---------|-------------------------------|--------------------------|
| requestCreateBoard  | POST    | /board                        | 게시판 생성 요청         |
| searchBoard         | GET     | /board/{isApprove}            | 승인된 게시판 조회       |

## 🎞시연 영상

https://github.com/lth01/ormi-community/assets/139758405/a0ee498f-a7e4-4640-a62e-661d693c177f

https://github.com/lth01/ormi-community/assets/139758405/8b932928-b806-47aa-8b2b-f31235d78359

https://github.com/lth01/ormi-community/assets/139758405/9ab64b24-33f5-48f2-9f5f-c7e4e7e796d2

https://github.com/lth01/ormi-community/assets/139758405/2dad5efc-d509-4098-9f3b-79637befee37

https://github.com/lth01/ormi-community/assets/139758405/f846baef-432b-4e98-9525-312e34f93f10

https://github.com/lth01/ormi-community/assets/139758405/b959897c-04e2-4969-bff0-3e675c7735d3


## 🛠Coding Convention

### Java Convention

[자바 컨벤션](https://github.com/lth01/ormi-community/wiki/01-Java-Coding-Convention#java-coding-convention)

### Other Convention

[기타 컨벤션](https://github.com/lth01/ormi-community/wiki/02-Other-Convention)

## 👨‍💻Participation Member
- 이태희
- 김요한
- 김경록

## Refactoring Member
- 김요한
