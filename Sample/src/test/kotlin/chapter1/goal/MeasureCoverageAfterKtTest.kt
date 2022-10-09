package chapter1.goal

import chapter1.goal.isStringLongAfter
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

internal class MeasureCoverageAfterKtTest {

    /**
     * @see isStringLongAfter
     * 리팩토링 이후 커버리지는 100%로 변경되었다.
     */
    @Test
    fun test() {
        val result = isStringLongAfter("abc")
        assertEquals(false, result)
    }

}