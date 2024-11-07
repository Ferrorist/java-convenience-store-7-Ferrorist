# Day 03 (2024.11.07.)

<br>

## 목차
* [DTO 생성](./Day_03.md#dto-생성)
* [올바른 입력값 판단 기능 구현](./Day_03.md#올바른-입력값-판단-기능-구현)
* [재고 물품 및 구매 물품 개수 비교](./Day_03.md#재고-물품-및-구매-물품-개수-비교)
* [현재 진행 상황](./Day_03.md#현재-진행-상황)

<br>

## [DTO 생성](./Day_03.md#목차)

구매 관련 Request의 정보를 전달하고자 DTO인 ```PurchaseRequest```를 생성했다.
```java
public record PurchaseRequest(String productName, int quantity) { }
```

<br>


## [올바른 입력값 판단 기능 구현](./Day_03.md#목차)

### Production Code
```java

```

<br>

### Test Code
```java
@Test
void validatePurchaseRequestTrueTest() {
    Assertions.assertTrue(purchaseService.validatePurchaseRequest("[사이다-3], [물-1]"));
}

@Test
void validatePurchaseRequestExceptionSingleTest() {
    Assertions.assertThrows(IllegalArgumentException.class, () -> {
        purchaseService.validatePurchaseRequest("[사이다3][물-2]");
    });
}

@ParameterizedTest
@CsvFileSource(resources = "/validatePurchaseRequestExceptionTestFile.csv")
void validatePurchaseRequestExceptionTest(String input) {
    Assertions.assertThrows(IllegalArgumentException.class, () -> {
        purchaseService.validatePurchaseRequest(input);
    });
}
```

<br>

### Test Resources

#### validatePurchaseRequestExceptionTestFile.csv
```
"{사이다-3}, [물2]"
"[사이다-3}"
"(사이다-8]"
"[물2], [물5]"
"[사이다-3]. [물-2]"
"[사이다3][물-2]"
"[사이다3}{물-2]"
"[사이다-2, 감자칩-1"
"사이다-2],[감자칩-1]"
"사이다-2,감자칩-1"
"([사이다-2],[감자칩-1])"
"[사이다=2],[감자칩=1]"
"[사이다:2],[감자칩:1]"
"[사이다-],[감자칩-]"
"[사이다--2],[감자칩--1]"
"[사이다-2a],[감자칩-1b]"
"[사이다@-2],[감자칩#-1]"
"[-2],[감자칩-1]"
"[사이다-2],,[감자칩-1]"
"[사이다-2];[감자칩-1]"
"[사이다-0],[감자칩-1]"
```

<br><br>

## [재고 물품 및 구매 물품 개수 비교](./Day_03.md#목차)

### Production Code
```java
```

<br>

### Test Code
```java
@Test
void checkProductStocksTest() {
    Assertions.assertDoesNotThrow(() -> {
        purchaseService.getPurchaseRequests("[사이다-3]");
    });
}

@Test
void checkProductStocksExceptionTest() {
    Assertions.assertThrows(IllegalArgumentException.class, () -> {
        purchaseService.getPurchaseRequests("[컵라면-100]");
    });
}
```

<br><br>

## [현재 진행 상황](./Day_03.md#목차)
- [x] 환영 문구 출력 기능 구현
- [x] 상품 및 프로모션 객체 생성
- [x] md 파일 읽기 기능 구현
- [x] 보유 상품 목록 불러오기
- [x] 프로모션 목록 불러오기
- [x] 상품 목록 출력
- [x] 구매 상품 및 수량 입력 안내 출력
    - [x] 사용자 구매 물품 입력 받기
    - [x] 올바른 입력값 판단 기능 구현
    - [x] 재고 물품 및 구매 물품 개수 비교
- [ ] 구매 물품 리스트 생성
- [ ] 구매 물품 프로모션 적용 기능 구현
- [ ] 멤버십 할인 기능 구현
- [ ] 금액 계산 기능 구현
- [ ] 영수증 기능 구현
- [ ] 각 기능 에러 호출 시 반복 수행 기능 구현
- [ ] 재구매 여부 체크






### Production Code
```java
```

<br>

### Test Code
```java
```