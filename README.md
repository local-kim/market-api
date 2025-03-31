# market-api
> Spring Boot 기반의 간단한 REST API 서버

<br>

## 기술 스택
- Java 17, Spring Boot 3.4.1, Gradle 8.11.1
- Spring Security, JWT
- MySQL, Redis
- AWS S3

<br>

## 로컬에서 테스트하기
### 프로젝트 클론
```
git clone https://github.com/local-kim/market-api.git
cd market-api
```

### 환경설정
1. `src/main/resources/application.yml.template` 파일을 복사해서 `application.yml`로 변경합니다.
    ```
    cp src/main/resources/application.yml.template src/main/resources/application.yml
    ```
2. `YOUR_BUCKET_NAME` 같은 항목에 실제 값을 입력합니다.

### 실행
```
docker compose up --build -d
```

<br>

## API 문서
```
http://localhost:8080/swagger-ui/index.html
```
