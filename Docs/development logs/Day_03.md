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
public static boolean checkPurchaseRequestInputFormat(String input) throws IllegalArgumentException {
    char[] searchChars = {',', '[', ']'};
    int[] charCounts = countChars(input, searchChars);
    return (charCounts[0] + 1) == charCounts[1] && charCounts[1] == charCounts[2];
}

private static int[] countChars(String input, char[] searchChars) {
    int[] charCounts = {0, 0, 0};
    for (char inputChar : input.toCharArray()) {
        int idx = Arrays.binarySearch(searchChars, inputChar);
        if (StringUtils.checkSpecialChar(inputChar) && idx < 0) {
            throw new IllegalArgumentException(ErrorCode.NOT_SUPPORT_REQUEST_FORMAT.getMessage());
        }
        if (idx >= 0) {
            charCounts[idx]++;
        }
    }
    return charCounts;
}

public static void checkPurchaseRequestFormat(String input)  {
    if (!(input.startsWith("[") && input.endsWith("]") && input.contains("-"))) {
        throw new IllegalArgumentException(ErrorCode.NOT_SUPPORT_REQUEST_FORMAT.getMessage());
    }
    String[] values = input.substring(1, input.length() - 1).split("-");
    if(values.length != 2) {
        throw new IllegalArgumentException(ErrorCode.NOT_SUPPORT_REQUEST_FORMAT.getMessage());
    }
    if(StringUtils.checkAndparseInt(values[1]) <= 0) {
        throw new IllegalArgumentException(ErrorCode.NOT_SUPPORT_REQUEST_FORMAT.getMessage());
    }
}
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
private PurchaseRequest generatePurchaseRequest(String inputRequest) {
    String[] inputValue = inputRequest.substring(1, inputRequest.length() - 1).split("-");
    if (productManager.getProductByName(inputValue[0]) == null) {
        throw new IllegalArgumentException(ErrorCode.NOT_FOUND_PRODUCT.getMessage());
    }
    int requestQuantity = Integer.parseInt(inputValue[1]);
    if (requestQuantity > productManager.getProductStockByName(inputValue[0])) {
        throw new IllegalArgumentException(ErrorCode.EXCEED_REQUEST_QUANTITY.getMessage());
    }
    return new PurchaseRequest(inputValue[0], requestQuantity);
}
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
- [x] 구매 물품 리스트 생성
- [ ] 구매 물품 프로모션 적용 기능 구현
- [ ] 멤버십 할인 기능 구현
- [ ] 금액 계산 기능 구현
- [ ] 영수증 기능 구현
- [ ] 각 기능 에러 호출 시 반복 수행 기능 구현
- [ ] 재구매 여부 체크