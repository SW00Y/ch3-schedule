### [ Spring 입문 프로젝트 ]

## 개발 환경
> IDE : IntelliJ IDEA Community Edition  
> JDK : OpenJDK 17  
> SpringBoot : 3.3.3  

## 디렉토리 구조  

```md
src
├── ScheduleApplication.java  // 메인 애플리케이션
├── controller
│   ├── schedule
│   │   └── ScheduleController.java  // Schedule API 엔드포인트
│   └── user
│       └── UserController.java      // User API 엔드포인트
├── dto
│   ├── schedule
│   │   ├── ScheduleRequestDto.java   // Schedule 요청 DTO
│   │   └── ScheduleResponseDto.java  // Schedule 응답 DTO
│   └── user
│       ├── UserRequestDto.java       // User 요청 DTO
│       └── UserResponseDto.java      // User 요청 DTO
├── entity
│   ├── schedule
│   │   └── Schedule.java  // 스케줄 엔티티
│   └── user
│       └── User.java      // 유저 엔티티
├── exception
│   ├── CustomException.java          // 커스텀 예외
│   ├── ExceptionDto.java             // 예외 응답 형식
│   ├── ExceptionErrorCode.java       // 에러 코드 정의
│   └── GlobalExceptionHandler.java   // 전역 예외 처리
├── repository
│   ├── schedule
│   │   ├── JdbcTemplateScheduleRepository.java  // JDBC Schedule 구현체
│   │   └── ScheduleRepository.java             // 인터페이스
│   └── user
│       ├── JdbcTemplateUserRepository.java     // JDBC Schedule 구현체
│       └── UserRepository.java                 // 인터페이스
└── service
├── schedule
│   ├── ScheduleService.java      // Schedule 서비스 인터페이스
│   └── ScheduleServiceImpl.java  // 구현체
└── user
├── UserService.java             // User 서비스 인터페이스
└── UserServiceImpl.java         // 구현체
```

## API 문서
[![Swagger](https://raw.githubusercontent.com/SW00Y/ch3-schedule/main/docs/swagger/screenshot.png)](https://swooy.github.io/ch3-schedule/swagger/index.html)

## ERD


## 트러블 슈팅 및 예외처리  

트러블슈팅 JSON 시간대 DB에는 잘 저장되는데 RETURN이 안됨  

트러블슈팅 httpmessage 안나옴 -> enum, handler로 처리