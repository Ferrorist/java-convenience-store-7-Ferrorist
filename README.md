# java-convenience-store-precourse

<br>

## Git Commit Message Convention

[관련 문서 참고](./Docs/commit.md)

<br>

## Requirement

[관련 문서 참고](./Docs/requirement.md)

<br>

## To-do-List
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
- [x] 구매 물품 프로모션 적용 기능 구현
- [x] 멤버십 할인 기능 구현
- [x] 금액 계산 기능 구현
- [x] 영수증 기능 구현
- [x] 각 기능 에러 호출 시 반복 수행 기능 구현
- [x] 재구매 여부 체크

<br>

## 클래스 및 메서드 목록

test 코드를 제외한 프로젝트의 구조이다.


main  
├─java  
│  ├─store  
│  │  │  Application.java  
│  │  │  
│  │  ├─controller  
│  │  │      PaymentController.java  
│  │  │      PurchaseController.java  
│  │  │  
│  │  ├─enums  
│  │  │      ErrorCode.java  
│  │  │  
│  │  ├─factory  
│  │  │      ProductFactory.java  
│  │  │      PromotionFactory.java  
│  │  │  
│  │  ├─model  
│  │  │  │  Product.java  
│  │  │  │  Promotion.java  
│  │  │  │  
│  │  │  └─dto  
│  │  │      ├─request  
│  │  │      │      PurchaseRequest.java  
│  │  │      │  
│  │  │      └─response  
│  │  │              PaymentFreeResponse.java  
│  │  │              PaymentPriceResponse.java  
│  │  │              PaymentProductResponse.java  
│  │  │              PurchaseResponse.java  
│  │  │  
│  │  ├─service  
│  │  │  │  PaymentService.java  
│  │  │  │  PurchaseService.java  
│  │  │  │  
│  │  │  └─manager  
│  │  │          ProductManager.java  
│  │  │          PromotionManager.java  
│  │  │  
│  │  └─view  
│  │          InputView.java  
│  │          OutputView.java  
│  │  
│  └─[util](/Docs/product_desciprtions/java/util/README.md)  
│          InputCheckerUtils.java  
│          MarkDownUtils.java  
│          RecursiveUtils.java  
│          StringUtils.java  
│  
└─resources  
        products.md  
        promotions.md  
