# InputCheckUtils

## Methods

| Method Name | Return Type | Parameters | Throw Exception | Description |
| --- | --- | --- | --- | --- |
| [```checkPurchaseRequestInputFormat```](/src/main/java/util/InputCheckerUtils.java#L9) | ```boolean``` | ```String``` | ```IllegalArgumentException``` | 주어진 문자열이 구매 요청에 올바른 format인지 검사합니다.<br>부적합한 특수문자가 있을 경우 예외를 반환합니다.<br>올바른 format은 true, 그렇지 않으면 false를 반환합니다. |
| [```countChars```](/src/main/java/util/InputCheckerUtils.java#L15) | ```int[]``` | ```String, char[]``` | ```IllegalArgumentException``` | 주어진 문자열이 지원하는 특수문자가 각각 몇개인지를 반환합니다.<br>지원하지 않는 특수문자가 있을 경우, 예외를 발생시킵니다. |
| [```checkPurchaseRequestFormat```](/src/main/java/util/InputCheckerUtils.java#L29) | ```void``` | ```String``` | ```IllegalArgumentException``` | 한 물품의 구매 요청 문자열이 적합한 요청인지 판단합니다.<br>부적절한 요청의 경우 예외를 발생시킵니다. |
| [```checkAnswerYesOrNo```](/src/main/java/util/InputCheckerUtils.java#L42) | ```boolean``` | (null) | (null) | 콘솔창의 Y 또는 N 입력값에 따라 값을 반환합니다.<br>Y(또는 y)를 입력할 경우 true, N(또는 n)을 입력할 경우 false를 반환합니다.<br>이외의 입력의 경우 다시 입력하도록 합니다. |