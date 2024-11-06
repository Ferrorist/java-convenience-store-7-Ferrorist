# Day 01 (2024.11.05.)

## 목차

* [요구사항 로직 분석](./Day_01.md#요구사항-로직-분석)
* [To-do-List 작성](./Day_01.md#to-do-list-작성)
* [환영 문구 출력 기능 구현](./Day_01.md#환영-문구-출력-기능-구현)
* [상품 및 프로모션 객체 생성](./Day_01.md#상품-및-프로모션-객체-생성)
* [현재 진행 상황](./Day_01.md#현재-진행-상황)



## [요구사항 로직 분석](./Day_01.md#목차)

0. 환영 문구 출력
1. 보유 상품 목록, 프로모션 목록 가져오기
2. 상품 목록 및 가격, 프로모션, 재고 안내 출력
3. 구매 상품 및 수량 입력 안내 출력
4. 사용자에게 구매 상품 문자열 입력받기
5. 구매 상품 문자열이 올바른 입력값인지 검사
    * 입력 형식이 올바른가
    * 존재하지 않는 상품을 요청하였는가
    * 구매 수량이 재고 수량을 초과하였는가
    * 입력값이 올바르지 않다고 판단될 경우 에러 출력 및 5번으로 다시 이동

6. 입력받은 문자열을 통해 구매 리스트를 생성
7. 각 물품의 프로모션 여부 확인. 
    * 프로모션이 적용되어 있는 물품의 경우 프로모션 기한 확인.
    * 구매 물품에 프로모션 물품 유무 확인
    * 요청 물품 개수와 프로모션 적용 상품 개수 비교
        * 요청 물품 개수 % (promotion.buy + promotion.get) != 0 인 경우<br>
        프로모션으로 무료로 받을 수 있는 재고가 있는지 확인 후 혜택 출력

        * 프로모션 재고 < 요청 물품 개수의 경우,<br>
        프로모션 미할인 적용 안내 문구 출력

8. 멤버십 할인 유무 체크
9. 총구매액, 행사할인, 멤버십 할인, 내실 돈 계산
10. 영수증 출력
11. 재구매 의향 입력받기
12. 종료

## [To-do-List 작성](./Day_01.md#목차)

- [ ] 환영 문구 출력 기능 구현
- [ ] 상품 및 프로모션 객체 생성
- [ ] md 파일 읽기 기능 구현
- [ ] 보유 상품 목록 불러오기
- [ ] 프로모션 목록 불러오기
- [ ] 상품 목록 출력
- [ ] 구매 상품 및 수량 입력 안내 출력
    - [ ] 사용자 구매 물품 입력 받기
    - [ ] 올바른 입력값 판단 기능 구현
    - [ ] 재고 물품 및 구매 물품 개수 비교
- [ ] 구매 물품 리스트 생성
- [ ] 구매 물품 프로모션 적용 기능 구현
- [ ] 멤버십 할인 기능 구현
- [ ] 금액 계산 기능 구현
- [ ] 영수증 기능 구현
- [ ] 각 기능 에러 호출 시 반복 수행 기능 구현
- [ ] 재구매 여부 체크

## [환영 문구 출력 기능 구현](./Day_01.md#목차)
```java
public class OutputView {
    public void printWelcomeMessage() {
        System.out.println("안녕하세요. W편의점입니다.");
    }
}
```
사용자에게 메시지를 출력하는 기능을 담당하는 클래스인 ```OutputView```를 생성했다.


```java
public class Application {
    private static OutputView outputView = new OutputView();

    public static void main(String[] args) {
        startProgram();
    }

    private static void startProgram() {
        outputView.printWelcomeMessage();
    }
}
```

## [상품 및 프로모션 객체 생성](./Day_01.md#목차)
```java
public class Product {
    private final String name;
    private final int price;
    private int quantity;
    private final Promotion productPromotion;


    public Product(String name, int price, int quantity, Promotion productPromotion) {
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.productPromotion = productPromotion;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
```

```java
package store.model;

public class Promotion {
    private String name;
    private int buyQuantity;
    private int freeQuantity;

    private String startDate;
    private String endDate;

    public Promotion(String name, int buyQuantity, int freeQuantity, String startDate, String endDate) {
        this.name = name;
        this.buyQuantity = buyQuantity;
        this.freeQuantity = freeQuantity;
        this.startDate = startDate;
        this.endDate = endDate;
    }
}
```

## [현재 진행 상황](./Day_01.md#목차)
- [x] 환영 문구 출력 기능 구현
- [x] 상품 및 프로모션 객체 생성
- [ ] md 파일 읽기 기능 구현
- [ ] 보유 상품 목록 불러오기
- [ ] 프로모션 목록 불러오기
- [ ] 상품 목록 출력
- [ ] 구매 상품 및 수량 입력 안내 출력
    - [ ] 사용자 구매 물품 입력 받기
    - [ ] 올바른 입력값 판단 기능 구현
    - [ ] 재고 물품 및 구매 물품 개수 비교
- [ ] 구매 물품 리스트 생성
- [ ] 구매 물품 프로모션 적용 기능 구현
- [ ] 멤버십 할인 기능 구현
- [ ] 금액 계산 기능 구현
- [ ] 영수증 기능 구현
- [ ] 각 기능 에러 호출 시 반복 수행 기능 구현
- [ ] 재구매 여부 체크