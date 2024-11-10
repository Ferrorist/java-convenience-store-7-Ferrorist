# RecursiveUtils

주어진 기능들을 예외가 발생하지 않을 때까지 반복수행 하도록 하는 클래스입니다.

## Methods

| Method Name | Return Type | Parameters | Throw Exception | Description |
| --- | --- | --- | --- | --- |
| [```executeUntilNoException```](/src/main/java/util/RecursiveUtils.java#L6) | ```T``` | ```Supplier<T>``` | (null) | supplier로 주어진 작업들이 도중에 예외를 발생시키지 않을 때까지 반복수행합니다.<br>예외가 발생할 경우, 반환된 예외의 메시지를 출력한 후, 주어진 작업을 처음부터 수행합니다.  |