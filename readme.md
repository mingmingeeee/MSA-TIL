# MicroServiceArchitecture
- 각각을 분리시킴 + 자동화된 배포 시스템
- 운영, 유지보수에 편리
- 의미에 맞게 Service가 나눠지고 DB도 각각에 맞게 사용

# SpringCloud
- **마이크로 서비스를 제공하기 위한 프레임워크**

**Java application들이 클라우드 상태로서 분리될 수 있는 “Micro Service”**

- 쓸 것
    - **Spring Cloud Config - 환경 설정**
        - 마이크로 서비스에서 사용할 수 있는 정보를 ConfigServer 통해서 외부 저장소에 저장 가능 (Gateway ID, token 정보 등)
    - **Spring Cloud Netflix**
        - Location transparency
            - 서비스의 등록, 위치 정보 확인, 검색 ⇒ Eureka 서버 (Naming 서버_
    - **Spring Cloud Security**
    - **Spring Cloud Starters**
    - **Spring Cloud Gateway (Load Balancing) - Naming 서버 등록해 놓고 위치 검색**
        - 서버에 들어왔던 요청 정보 분산
        - Gateway 기능
    - **Spring Cloud OpenFegin**
        - 마이크로서비스 간 통신 위해서는 Rest나 Feign 필요
    - 모니터링, 가식화 (로그 추적)
        - Zipkin
        - Netflix API gateway
    - 장애 복구 위한 회복성 패턴
    - Hystrix (Netflix에 있는)


![543](https://user-images.githubusercontent.com/45472076/231830204-b316178e-66ca-442c-8342-0bf070a680d5.PNG)
**Client → (정보 요청) → LoadBalancer(API Gateway) → (내가 필요한 서비스가 어디입니까)? → Discovery → 마이크로서비스**

# Eureka

- **Service Discovery**
    - 외부의 다른 서비스들이 마이크로 서비스 검색하기 위함
- 기능
    - 서비스 등록
    - 서비스 검색
    
# SpringSecurity
- Authentication + Authorization
- Authentication: 인증
- Authorization: 권한
인증이 되었을 때 or 인증이 안 되었을 때 작업 처리

(1) application에 spring security jar을 Dependency에 추가

(2) WebSecurityConfigurerAdapter 상속받는 Security Configuration class 생성

(3) Security Configuration class에 @EnableWebSecurity 추가

(4) **Authentication** -> configure(AuthenticationManagerBuilder auth) 메서드 재정의

(5) Password encode위한 BCryptPasswordEncoder 빈 정의

랜덤 Salt 부여하여 여러번 Hash 적용한 암호화 방식

(6) **Authorization** -> configure(HttpSecurity http) 메서드 재정의

# Spring Security 순서
![gggg](https://user-images.githubusercontent.com/45472076/232300894-9b169f09-b0fd-46a1-a324-e0a5652abe68.PNG)
(1) config 

(2) 사용자 Login시 가장 먼저 AuthenticationFilter - attemptAuthentication 실행

=> Login 요청

(3) loadUserByUsername 실행

=> DB에서 존재 여부 확인 

(4) successfulAuthentication 실행 (Login 완료 시)

=> Token 발급

==> 발급 받을 땐 builer.

==> 검증할 땐 parser. => decode 해서 검증
