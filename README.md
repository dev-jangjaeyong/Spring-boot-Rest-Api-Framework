# Spring boot rest api framework sample
- gradle
- spring boot 2.0.5
- Json Web Token(이하 JWT), spring security, JPA, MyBatis, Redis, lombok
- 본 소스는 전체를 담고있지 않습니다. 참조용으로만 사용가능합니다.
- 모든 소스 및 framework 구조는 직접 작성되었습니다.

## Spring Security와 JWT 연동
  - 로그인 후 spring security에서 session 생성 및 유저 정보관리
  - 일반 JWT 생성 및 Refresh JWT 발급 : 일반 JWT는 response / refresh token 은 data로 별도 관리
  - 서비스에서 로그인 정보를 가지지 않도록 함
  - role, role Hierarchy, 권한 api, 권한 화면menu 등은 database에서 관리
  - 프로세스는 config > aop / security 디렉토리 참조
  
## code관리
  - database에서 read 하지 않게 singleton으로 관리
  - 코드 변경시 메모리에 재적재하기 위해 reinit 제공
  - config > define 디렉토리 참조

## query 수행시 data의 자동 암복호화를 위한 처리
  - MyBatis : TypeHandler
  - JPA : EntityInterceptor
  - config > typeHandler 디렉토리 참조

## redis 연동
  - RedisConfig Class, RedisProperties Class, RedisUtil Class 참조

## multi datbase
  - config > datasource 디렉토리 참조

## response template
  - core > api 디렉토리 참조
  - ApiResponse class / ApiStaus class

## filter / interceptor
  - core > interceptor 참조
  - sql injection / xss 공격 방어
  - apiFilter / SqlFilter / XssFilter
  - 특이점 : apiFilter에서 request 를 조작할 수 있음
             request body / model attribute 자료형 모두 제공

## JPA / MyBatis
  - 동적분기가 많은 SQL에 대해 JPA 대신 MyBatis를 사용할 수 있게함.

## Queue Manager / Worker
  - core > worker 디렉토리 참조
  - log를 저장할 때 queue에 담아 queue size 가 max 또는 일정 주기로 drain
