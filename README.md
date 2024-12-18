# DeliveryMiniProject

## 팀원 및 역할

- 사용자 / 인증 - `박송이`
- 가게, 상품, 리뷰 - `김재현`
- 카테고리, 지역, 배포, AI - `정선모`
- 장바구니, 주문, 결제 - `이건`

## 서비스 구성 및 실행방법

### 프로젝트 구조

- Monolithic Application
- Domain Architecture
- Entity 및 DTO
- RESTful API 설계
- Exception Handling

### 폴더 구조

```
DeliveryMiniProject
├─ .gitattributes
├─ .gitignore
├─ README.md
├─ build
├─ gradle
├─ gradlew
├─ gradlew.bat
└─ src
   ├─ main
   │  ├─ java
   │  │  └─ com
   │  │     └─ sparta
   │  │        └─ deliveryminiproject
   │  │           ├─ DeliveryMiniProjectApplication.java
   │  │           ├─ domain
   │  │           │  ├─ cart
   │  │           │  │  ├─ controller
   │  │           │  │  ├─ dto
   │  │           │  │  ├─ entity
   │  │           │  │  ├─ repository
   │  │           │  │  └─ service
   │  │           │  ├─ category
   │  │           │  │  ├─ controller
   │  │           │  │  ├─ dto
   │  │           │  │  ├─ entity
   │  │           │  │  ├─ repository
   │  │           │  │  └─ service
   │  │           │  ├─ menu
   │  │           │  │  ├─ controller
   │  │           │  │  ├─ dto
   │  │           │  │  ├─ entity
   │  │           │  │  ├─ repository
   │  │           │  │  └─ service
   │  │           │  ├─ order
   │  │           │  │  ├─ controller
   │  │           │  │  ├─ dto
   │  │           │  │  ├─ entity
   │  │           │  │  ├─ repository
   │  │           │  │  └─ service
   │  │           │  ├─ payment
   │  │           │  │  ├─ entity
   │  │           │  │  ├─ repository
   │  │           │  │  └─ service
   │  │           │  ├─ region
   │  │           │  │  ├─ dto
   │  │           │  │  └─ entity
   │  │           │  ├─ review
   │  │           │  │  ├─ controller
   │  │           │  │  ├─ dto
   │  │           │  │  ├─ entity
   │  │           │  │  ├─ repository
   │  │           │  │  └─ service
   │  │           │  ├─ shop
   │  │           │  │  ├─ controller
   │  │           │  │  ├─ dto
   │  │           │  │  ├─ entity
   │  │           │  │  ├─ repository
   │  │           │  │  └─ service
   │  │           │  └─ user
   │  │           │     ├─ controller
   │  │           │     ├─ dto
   │  │           │     ├─ entity
   │  │           │     ├─ repository
   │  │           │     └─ service
   │  │           └─ global
   │  │              ├─ config
   │  │              ├─ entity
   │  │              ├─ exception
   │  │              ├─ jwt
   │  │              ├─ response
   │  │              └─ security
   │  └─ resources
   └─ test
```

### 실행 방법

1. /resources/application.properties
   ```
      # local DB Setting
      #spring.datasource.driverClassName=org.postgresql.Driver
      #spring.datasource.url=jdbc:postgresql://localhost:5432/{dbname}
      #spring.datasource.username={username}
      #spring.datasource.password={password}
      
      # Release DB Setting
      spring.datasource.driverClassName=org.postgresql.Driver
      spring.datasource.url=jdbc:postgresql://{domain}:5432/{dbname}
      spring.datasource.username={username}
      spring.datasource.password={password}
   
      #spring.jpa.properties.hibernate.default_schema = test
      # maximum connection pool size
      spring.datasource.hikari.maximum-pool-size=4
   
      spring.jpa.hibernate.ddl-auto=update
   
      spring.jpa.properties.hibernate.show_sql=true
      spring.jpa.properties.hibernate.format_sql=true
      spring.jpa.properties.hibernate.use_sql_comments=true
   
      spring.data.redis.host=localhost
      spring.data.redis.port=6379
      spring.data.redis.timeout=6000
   
      # Ai API Setting
      gemini.api.url=https://generativelanguage.googleapis.com/v1beta/models/gemini-1.5-flash-latest:generateContent
      gemini.api.key={your.key}
   
      jwt.secret.key={jwt.secret.key}
   ```

2. 레포지토리 복제

    ```
    git clone https://github.com/wjdtjsah12/DeliveryMiniProject.git
    cd DeliveryMiniProject
    ```

3. 의존성 설치

    ```
    ./gradlew build
    ```

4. 실행

    ```
    ./gradlew bootRun
    ```

5. 실행 후, 기본적으로 애플리케이션은 `http://localhost:8080`에서 확인할 수 있습니다.

### 인프라 구조

![image](https://github.com/user-attachments/assets/e6a2c095-4b90-468f-a92b-a8b1ed4e7a9a)

## 프로젝트 목적/상세

### 프로젝트 목적

```
사용자가 손쉽게 음식을 주문하고 관리할 수 있는 AI 연동 음식 주문 관리 플랫폼 구축
```

- 사용자가 음식 주문을 보다 쉽게 할 수 있도록 돕기 위한 음식 주문 관리 플랫폼입니다.
- 고객은 회원가입 후 다양한 음식점에서 음식을 선택하고 장바구니에 담은 뒤, 결제까지의 과정을 간편하게 진행할 수 있습니다.
- 또한, AI 기반의 메뉴 설명과 음식을 카테고리별로 구분하여 제공함으로써, 사용자 경험을 한층 향상시킵니다.

### 프로젝트 상세

- 사용자 관리
- 장바구니 관리
- 주문 관리
- 상점 관리
- 메뉴 관리
- 리뷰 관리
- 카테고리 관리
- 지역 관리
- Gemini 연동

## 기술 스택

![Java](https://img.shields.io/badge/java(17)-%23ED8B00.svg?style=for-the-badge&logo=openjdk&logoColor=white)
![Spring](https://img.shields.io/badge/spring(3.3.5)-%236DB33F.svg?style=for-the-badge&logo=spring&logoColor=white)
![JPA](https://img.shields.io/badge/JPA(6.5.3)-%1997B54e.svg?style=for-the-badge)
![SpringSecurity](https://img.shields.io/badge/SpringSecurity-168363?style=for-the-badge)
![JWT](https://img.shields.io/badge/JWT(0.11.5)-black?style=for-the-badge&logo=JSON%20web%20tokens)
![Redis](https://img.shields.io/badge/redis(7.2.6)-%23DD0031.svg?style=for-the-badge&logo=redis&logoColor=white)
![Postgres](https://img.shields.io/badge/postgresql(17.1)-%23316192.svg?style=for-the-badge&logo=postgresql&logoColor=white)
![AWS](https://img.shields.io/badge/AWS-%23FF9900.svg?style=for-the-badge&logo=amazon-aws&logoColor=white)
![Git](https://img.shields.io/badge/git-%23F05033.svg?style=for-the-badge&logo=git&logoColor=white)
![Notion](https://img.shields.io/badge/Notion-%23008DE4.svg?style=for-the-badge&logo=notion&logoColor=white)

## ERD Docs

- https://www.erdcloud.com/d/LHCEMwe9PN5qfokkg

## API Docs

- https://www.notion.so/teamsparta/26-S-A-c91495224b444024a68a65ed4b92223c