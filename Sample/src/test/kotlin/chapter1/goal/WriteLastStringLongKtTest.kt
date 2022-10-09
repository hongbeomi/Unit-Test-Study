package chapter1.goal

import chapter1.goal.writeLastIsStringLong
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

internal class WriteLastStringLongKtTest {

    /**
     * @see writeLastIsStringLong
     * 아래의 테스트는 writeLastIsStringLong 의 두 번째 result 만 검증된다.
     */
    @Test
    fun test() {
        val result = writeLastIsStringLong("abc")
        assertEquals(false, result)
    }

}