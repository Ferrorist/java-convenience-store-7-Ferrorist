# Day 02 (2024.11.06.)

## 목차

<br>

* [md 파일 읽기 기능 구현](./Day_02.md#md-파일-읽기-기능-구현)
* [프로모션 목록 불러오기](./Day_02.md#프로모션-목록-불러오기)
* [상품 목록 불러오기](./Day_02.md#상품-목록-불러오기)
* [현재 진행 상황](./Day_02.md#현재-진행-상황)

<br>

## [md 파일 읽기 기능 구현](./Day_02.md#목차)

### Production Code
```java
public class MarkDownUtils {
    public static List<String> readMarkDownFile(String filePath) {
        return readMarkDownFile(new File(filePath));
    }

    public static List<String> readMarkDownFile(File file) {
        try {
            return readFile(file);
        } catch (FileNotFoundException exception) {
            System.out.println("[ERROR] 파일을 찾을 수 없습니다.");
        } catch (IOException exception) {
            System.out.println("[ERROR] 파일을 읽는 도중 에러가 발생했습니다.");
        }

        return new ArrayList<>();
    }

    private static List<String> readFile(File file) throws IOException {
        List<String> markdownLines = new ArrayList<>();
        BufferedReader br = new BufferedReader(new FileReader(file));
        String line;
        while ((line = br.readLine()) != null) {
            markdownLines.add(line);
        }
        return markdownLines.subList(1, markdownLines.size());
    }
}
```

<br>

### Test Code
```java
private final String productionPromotionFilePath = "src/main/resources/promotions.md";

@Test
void readMarkDownFileTest() {
    Assertions.assertDoesNotThrow(() -> {
        MarkDownUtils.readMarkDownFile(productionPromotionFilePath);
    });
}
```

<br><br>

## [프로모션 목록 불러오기](./Day_02.md#목차)

### Production Code

#### InventoryManager

```java
public class InventoryManager {
    private static final String PROMOTIONS_FILE_PATH = "src/main/resources/promotions.md";
    private static InventoryManager instance;
    private List<Promotion> promotions;
    private Map<String, Promotion> promotionsByNames;

    private InventoryManager() {
        promotions = null;
        promotionsByNames = null;
    }

    public static InventoryManager getInstance() {
        if (instance == null) {
            instance = new InventoryManager();
        }
        return instance;
    }

    public List<Promotion> getPromotions() {
        initPromotions();
        return promotions;
    }

    public void addPromotion (Promotion promotion) {
        initPromotions();
        promotions.add(promotion);
        promotionsByNames.put(promotion.getName(), promotion);
    }

    public Promotion searchPromotionByName(String promotionName) throws ClassNotFoundException {
        initPromotions();
        Promotion promotion = promotionsByNames.getOrDefault(promotionName, null);
        if(promotion == null)   {
            throw new ClassNotFoundException("[ERROR] 찾을 수 없는 프로모션 입니다.");
        }
        return promotion;
    }

    private void initPromotions() {
        if (promotions == null) {
            promotions = new ArrayList<>();
            promotionsByNames = new HashMap<>();
            List<String> promotionLines = MarkDownUtils.readMarkDownFile(PROMOTIONS_FILE_PATH);
            for (String line : promotionLines) {
                Promotion promotion = PromotionFactory.createPromotion(line);
                promotions.add(promotion);
                promotionsByNames.put(promotion.getName(), promotion);
            }
        }
    }
}
```

<br>

#### PromotionFactory

```java
public class PromotionFactory {
    public static Promotion createPromotion(String input) throws IllegalArgumentException {
        return createPromotion(StringUtils.splitLinetoArray(input));
    }

    public static Promotion createPromotion(String[] inputs) throws IllegalArgumentException {
        if (checkInputArgument(inputs)) {
            int buyQuantity = checkAndparseInt(inputs[1]);
            int freeQuantity = checkAndparseInt(inputs[2]);

            return new Promotion(inputs[0], buyQuantity, freeQuantity, inputs[3], inputs[4]);
        }
        return null;
    }

    private static boolean checkInputArgument(String[] inputs) {
        if ((inputs.length != Promotion.class.getDeclaredFields().length) ||
                !(StringUtils.checkDatePattern(inputs[3]) && StringUtils.checkDatePattern(inputs[4]))) {
            throw new IllegalArgumentException("[ERROR] 올바르지 않는 프로모션 입력값입니다.");
        }

        return true;
    }

    private static int checkAndparseInt(String input) {
        try {
            return Integer.parseInt(input);
        } catch (NumberFormatException exception) {
            throw new NumberFormatException("[ERROR] 올바르지 않는 프로모션 입력값입니다.");
        }
    }
}
```

### Test Code

```java
public class FactoryTest {
    @Test
    void GeneratePromotionTest() {
        Assertions.assertEquals(
                new Promotion("A", 1, 2, "2000-1-1", "2000-02-01"),
                PromotionFactory.createPromotion("A, 1, 2, 2000-1-1, 2000-02-01")
                );
    }

    @Test
    void IllegalArugmentExceptionTest() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            PromotionFactory.createPromotion("A, 1, 2, B");
        });
    }

    @Test
    void NumberFormatExceptionTest() {
        Assertions.assertThrows(NumberFormatException.class, () -> {
            PromotionFactory.createPromotion("A, B, C, D, E");
        });
    }
}
```

<br><br>

## [상품 목록 불러오기](./Day_02.md#목차)

### Production Code

#### InventoryManager
```java
public class InventoryManager {
    private static final String PRODUCTS_FILE_PATH = "src/main/resources/products.md";
    private static InventoryManager instance;
    private List<Product> products;
    private InventoryManager() {
        products = null;
        promotions = null;
        promotionsByNames = null;
    }

    public List<Product> getProducts() {
        initProducts();
        return products;
    }

    public void addProduct (Product product) {
        initProducts();
        products.add(product);
    }

    private void initProducts() {
        if (products == null) {
            products = new ArrayList<>();
            List<String> productLines = MarkDownUtils.readMarkDownFile(PRODUCTS_FILE_PATH);
            for (String line : productLines) {
                Product product = ProductFactory.createProduct(line);
                products.add(product);
            }
        }
    }
}
```

<br>

#### ProductFactory
```java
public class ProductFactory {

    private static final InventoryManager inventoryManager = InventoryManager.getInstance();

    public static Product createProduct (String input) throws IllegalArgumentException {
        return createProduct(StringUtils.splitLinetoArray(input));
    }
    public static Product createProduct (String[] inputs) throws IllegalArgumentException {
        if(checkInputArgument(inputs)) {
            String productName = inputs[0];
            int productPrice = StringUtils.checkAndparseInt(inputs[1]);
            int productQuantity = StringUtils.checkAndparseInt(inputs[2]);
            Promotion promotion = inventoryManager.searchPromotionByName(inputs[3]);
            return new Product(productName, productPrice, productQuantity, promotion);
        }

        return null;
    }

    private static boolean checkInputArgument(String[] inputs) {
        if (inputs.length != Product.class.getDeclaredFields().length
        || (!inputs[3].strip().equals("null")
                && inventoryManager.searchPromotionByName(inputs[3].strip()) == null)) {
            throw new IllegalArgumentException("[ERROR] 올바르지 않는 상품 입력값입니다.");
        }

        return true;
    }
}
```

<br><br>


## [현재 진행 상황](./Day_02.md#목차)
- [x] 환영 문구 출력 기능 구현
- [x] 상품 및 프로모션 객체 생성
- [x] md 파일 읽기 기능 구현
- [x] 보유 상품 목록 불러오기
- [x] 프로모션 목록 불러오기
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

