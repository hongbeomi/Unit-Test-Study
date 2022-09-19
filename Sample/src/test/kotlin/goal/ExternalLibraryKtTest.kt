package goal

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

internal class ExternalLibraryKtTest {

    /**
     * @see parse
     * 분기 커버리지 지표는 100%이며, 메서드 결과의 모든 구성 요소를 검증한다.
     * 하지만 Integer.parseInt 가 수행하는 코드 경로는 고려하지 않는다.
     * ex) null, non-number, empty
     */
    @Test
    fun test() {
        val result = parse("5")
        assertEquals(5, result)
    }

}