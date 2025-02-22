Step1: Entity 설계 및 데이터 접근 계층 구현
---

# 목표

> 게시판 Application을 구현하면서 JPA를 통해 Entity 모델을 구성하고, 객체와 테이블을 매핑하는 방법에 대해 학습합니다.

# 요구 사항

- 게시글 DDL을 통해 객체와 테이블의 관계를 Entity Class로 표현합니다.
- @DataJpaTest를 이용하여 Test profile에서 기본적인 JpaRepository의 동작을 학습하는 테스트를 작성합니다.

```sql
create table p_board
(
    id         uuid primary key,
    title      varchat(50),
    content    text,
    created_at timestamp not null,
    updated_at timestamp
);

COMMENT
ON TABLE p_board IS '게시판 테이블';
COMMENT
ON COLUMN p_board.id IS '게시글 식별자';
COMMENT
ON COLUMN p_board.title IS '게시글 제목';
COMMENT
ON COLUMN p_board.content IS '게시글 내용';
COMMENT
ON COLUMN p_board.created_at IS '게시글 생성 시간';
COMMENT
ON COLUMN p_board.updated_at IS '게시글 마지막 수정 시간';
```

# TODO, 구현 기능 목록

- [ ] 게시글 Entity 설계
    - [ ] `p_board`의 컬럼을 기준으로 Board Entity 필드 설계
- [ ] 게시글 Entity의 영속 처리를 위한 환경 설정
    - [ ] JpaConfig : JPA Auditing 사용을 위한 @EnableJpaAuditing 설정 적용
    - [ ] Auditing 적용을 위한 Annotation 추가 : @EntityListeners(AuditingEntityListener.class), @CreatedDate,
      @LastModifiedDate
- [ ] Test 환경 구성
    - [ ] test profile의 datasource, jpa 환경 설정 구성
        - [ ] h2 환경 설정 구성
        - [ ] jpa 환경 설정 구성
- [ ] 게시글 Repository 정의 및 TC 작성
    - [ ] 게시글 영속화를 위한 JpaRepository 정의
    - [ ] 게시글 Entity 생성을 위한 정적 팩터리 메서드 구현
    - [ ] @DataJpaTest를 이용한 게시글 저장 TC 작성
    - [ ] @DataJpaTest를 이용한 특정 게시글 조회 TC 작성
    - [ ] 존재 하지 않는 게시글 조회 시 예외 발생 TC 작성

# 구현 시 참고 사항

- `H2 In-memeory DB를 통해 Test하기 쉬운 환경을 구성`할 수 있습니다.

```yml
spring:
  config.activate.on-profile: test
  datasource:
    hikari.jdbc-url: jdbc:h2:mem:test_db;
    username: sa
    password:
```

- Test Profile 에서 `ddl-auto: create 설정을 통해 스키마를 편리하게 생성`할 수 있습니다.
- Test Profile 에서 JPA의 `show-sql`, `format_sql`, `highlight_sql`설정을 통해 손쉽게 실행 쿼리를 확인할 수 있습니다.

```yml
spring:
  config.activate.on-profile: test
  jpa:
    hibernate.ddl-auto: create
    show-sql: true
    properties:
      hibernate:
        format_sql: true
        highlight_sql: true
```

- 위 설정을 적절히 활용하여 아래와 같이 Test profile을 구성할 수 있습니다.

application-test.yml

```yml
spring:

  datasource:
    hikari.jdbc-url: jdbc:h2:mem:test_db;
    username: sa
    password:

  jpa:
    hibernate.ddl-auto: create
    show-sql: true
    properties:
      hibernate:
        format_sql: true
        highlight_sql: true
```
