# Spring boot shopping mall project

- IntelliJ  
- Spring boot 2.7.1  
- Gradle

## 1. 상품 엔티티 설계
- ItemSellStatus (enum class)  
  - SELL, SOLD_OUT

- Item (Entity class)  
  - 상품코드 Long id - PK  
  - 상품명 String itemNm  
  - 가격 int price  
  - 재고수량 int stockNumber  
  - 상품상세설명 String itemDetail  
  - 상품판매상태 ItemSellStatus itemSellStatus
  
## 2. Repository 설계

- ItemRepository extends JpaRepository<Item, Long>  
  - JpaRepository 를 상속 받는 인터페이스
  - Item : 엔티티 클래스
  - Long : PK 타입
  
## 3. Query Method

`find + (엔티티 이름) + By + 변수이름`

- findByItemNm
- findByItemNmOrItemDetail
- findByPriceLessThan
- findByPriceLessThanOrderByPriceDesc

## 4. Spring Data JPA @Query Annotation

`@Query("JPQL 쿼리문")`

- findByItemDetail
- findByItemDetailByNative

## 5. Spring Data JPA Querydsl

Querydsl : JPQL -> 코드로 작성할 수 있도록 도와주는 빌더 API

- build.gradle -> 라이브러리, 어노테이션프로세서 추가  
  `implementation 'com.querydsl:querydsl-jpa'`  
  `annotationProcessor "com.querydsl:querydsl-apt:5.0.0:jpa"`  
  `annotationProcessor "jakarta.persistence:jakarta.persistence-api"`  
  `annotationProcessor "jakarta.annotation:jakarta.annotation-api"`

- 빌드 후 `Qitem.java` 생성 