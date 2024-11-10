# StringUtils

## Methods

| Method Name | Return Type | Parameters | Throw Exception | Description |
| --- | --- | --- | --- | --- |
| [```splitLinetoArray```](/src/main/java/util/StringUtils.java#L12) | ```String[]``` | ```String``` | (null) | 주어진 문자열을 쉼표(,)를 기준으로 split한 결과를 배열로 반환합니다.<br>split된 각 문자열은 [strip()](https://docs.oracle.com/en/java/javase/21/docs/api/java.base/java/lang/String.html#strip())를 통해 양 옆의 공백이 제거됩니다. |
| [```splitLineToList```](/src/main/java/util/StringUtils.java#L22) | ```List<String>``` | ```String``` | (null) | 주어진 문자열을 쉼표(,)를 기준으로 split한 결과를 List으로 반환합니다.<br>split된 각 문자열은 [strip()](https://docs.oracle.com/en/java/javase/21/docs/api/java.base/java/lang/String.html#strip())를 통해 양 옆의 공백이 제거됩니다.  |
| [```checkDatePattern```](/src/main/java/util/StringUtils.java#L28) | ```boolean``` | ```String``` | (null) | 주어진 문자열이 일자 문자열의 패턴으로 구성되었는지 체크합니다.<br>검사하는 패턴은 다음과 같다.<br>```"^\\d{4}-\\d{2}-\\d{2}$"``` ex) 2024-01-31 |
| [```checkAndparseInt```](/src/main/java/util/StringUtils.java#L32) | ```int``` | ```String``` | ```NumberFormatException``` | 주어진 문자열을 int으로 변환하여 반환합니다.<br>변환할 수 없는 문자열의 경우 예외를 발생시킵니다. |
| [```checkSpecialChar```](/src/main/java/util/StringUtils.java#L40) | ```boolean``` | ```char``` | (null) | 주어진 char이 특수문자인지 체크합니다.<br>특수문자인 경우 true, 그렇지 않으면 false를 반환합니다. |
| [```convertToDecimalFormat```](/src/main/java/util/StringUtils.java#L44) | ```String``` | ```BigDecimal``` | (null) | 매개변수 값을 int으로 변경하여 ```convertToDecimalFormat(int amount)```의 리턴값을 반환합니다. |
| [```convertToDecimalFormat```](/src/main/java/util/StringUtils.java#L48) | ```String``` | ```int``` | (null) | 주어진 값을 일정 패턴에 맞추어서 반환하도록 합니다.<br>ex) 3000 → 3,000 |