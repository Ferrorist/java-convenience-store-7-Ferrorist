# Day 02 (2024.11.06.)

## 목차

<br>

* [md 파일 읽기 기능 구현](./Day_02.md#md-파일-읽기-기능-구현)
* [프로모션 목록 불러오기](./Day_02.md#프로모션-목록-불러오기)
* [상품 목록 불러오기](./Day_02.md#상품-목록-불러오기)
* [재고 목록 출력](./Day_02.md#재고-목록-출력)
* [문제 상황](./Day_02.md#문제-상황)
    * [문제 해결](./Day_02.md#문제-해결)
* [코드 리팩토링](./Day_02.md#코드-리팩토링)
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

## [재고 목록 출력](./Day_02.md#목차)

### Production Code
```java
public class OutputView {
    public static void printProducts() {
        System.out.println("현재 보유하고 있는 상품입니다.\n");
        System.out.println(generateProductListString());
    }

    private static String generateProductListString() {
        StringBuilder stringBuilder = new StringBuilder();
        for (Product product : InventoryManager.getInstance().getProducts()) {
            stringBuilder.append("- ");
            stringBuilder.append(product.getName()).append(" ");
            stringBuilder.append(new DecimalFormat("###,###").format(product.getPrice())).append("원 ");
            stringBuilder.append(product.getQuantitytoString()).append(" ");
            stringBuilder.append(product.getProductPromotionName()).append("\n");
        }

        return stringBuilder.toString();
    }
}
```

```java
public class Product {
    public String getQuantitytoString() {
        if(quantity > 0) {
            return Integer.toString(quantity) + "개";
        }
        return "재고 없음";
    }

    public String getProductPromotionName() {
        if (productPromotion == null) {
            return "";
        }
        return productPromotion.getName();
    }
}
```

<br><br>

## [문제 상황](./Day_02.md#목차)
```
- 콜라 1,000원 10개 탄산2+1
- 콜라 1,000원 10개 
- 사이다 1,000원 8개 탄산2+1
- 사이다 1,000원 7개 
- 오렌지주스 1,800원 9개 MD추천상품
- 탄산수 1,200원 5개 탄산2+1
- 물 500원 10개 
- 비타민워터 1,500원 6개 
- 감자칩 1,500원 5개 반짝할인
- 감자칩 1,500원 5개 
- 초코바 1,200원 5개 MD추천상품
- 초코바 1,200원 5개 
- 에너지바 2,000원 5개 
- 정식도시락 6,400원 8개 
- 컵라면 1,700원 1개 MD추천상품
- 컵라면 1,700원 10개
```

출력 당시의 문제는 바로 여기이다.
```
- 탄산수 1,200원 5개 탄산2+1
```

해당 재고의 경우 프로모션이 있는 재고는 있지만 그렇지 않은 재고는 0개 이므로 입력받지 않았으므로 출력되지 않는다.<br>
하지만 입력 받은 재고 중,<br>
프로모션이 있는 것만 입력받았을 경우 **같은 물품이지만 프로모션이 없는 재고 또한 표현하여야 한다.**<br>
그러므로 다음과 같이 표현이 되어야 한다는 의미이다.
```
- 탄산수 1,200원 5개 탄산2+1
- 탄산수 1,200원 재고 없음
```

<br>

### [문제 해결](./Day_02.md#목차)

0. Product를 관리하는 자료구조의 변경
```java
private Map<String, List<Product>> productsByName;  // Product 객체를 담는 HashMap
private Map<String, Integer> stockByName;           // 물품의 개수를 저장하는 HashMap
```

1. 입력된 적이 없는 물품의 경우, 프로모션이 없는 Product을 생성하여 List에 넣기.
```java
    List<Product> products = productsByName.getOrDefault(product.getName(), new ArrayList<>());
    if(products.isEmpty()) {
        products.add(new Product(product.getName(), product.getPrice(), 0, null));
    }
```

2. 이미 입력된 적이 있는 물품의 경우, 물품 개수 추가하기
```java
if(!insertProductToList(products, product)) {
            products.add(product);
}

private void registerProductStock(Product product) {
        int quantity = stockByName.getOrDefault(product.getName(), 0);
        stockByName.put(product.getName(), quantity + product.getQuantity());
}
```

3. Map의 Entry를 통해 재고 List 생성
```java
private List<Product> generateProductList() {
    List<Product> products = new ArrayList<>();
    for(Map.Entry<String, List<Product>> entry : productsByName.entrySet()) {
        products.addAll(entry.getValue());
    }
    Collections.sort(products);
    return products;
}
```

#### 최종 코드
```java
private void initProducts() {
    if (productsByName == null) {
        productsByName = new HashMap<>();
        stockByName = new HashMap<>();
        List<String> productLines = MarkDownUtils.readMarkDownFile(PRODUCTS_FILE_PATH);
        for (String line : productLines) {
            Product product = ProductFactory.createProduct(line);
            applyProduct(product);
        }
    }
}

private void applyProduct(Product product) {
    if (productsByName != null && stockByName != null) {
        registerProduct(product);
        registerProductStock(product);
    }
}

private void registerProduct(Product product) {
    List<Product> products = productsByName.getOrDefault(product.getName(), new ArrayList<>());
    if(products.isEmpty()) {
        products.add(new Product(product.getName(), product.getPrice(), 0, null));
    }
    if(!insertProductToList(products, product)) {
        products.add(product);
    }
    productsByName.put(product.getName(), products);
}

private boolean insertProductToList(List<Product> products, Product product) {
    for (Product value : products) {
        if (value.equals(product)) {
            int quantity = value.getQuantity() + product.getQuantity();
            value.setQuantity(quantity);
            return true;
        }
    }
    return false;
}

private void registerProductStock(Product product) {
    int quantity = stockByName.getOrDefault(product.getName(), 0);
    stockByName.put(product.getName(), quantity + product.getQuantity());
}

private List<Product> generateProductList() {
    List<Product> products = new ArrayList<>();
    for(Map.Entry<String, List<Product>> entry : productsByName.entrySet()) {
        products.addAll(entry.getValue());
    }
    Collections.sort(products);
    return products;
}
```
<br><br>

## [코드 리팩토링](./Day_02.md#목차)

Product와 Promotion을 관리하는 ```InventoryManager```가 많은 기능을 갖기 시작하여 이를 분리하였다.
```ProductManager```와 ```PromotionManager```으로 나누는 작업을 수행했다.

## [현재 진행 상황](./Day_02.md#목차)
- [x] 환영 문구 출력 기능 구현
- [x] 상품 및 프로모션 객체 생성
- [x] md 파일 읽기 기능 구현
- [x] 보유 상품 목록 불러오기
- [x] 프로모션 목록 불러오기
- [x] 상품 목록 출력
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

