package goal

import goal.isStringLongBefore
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class MeasureCoverageBeforeKtTest {

    /**
     * @see isStringLong
     * 이 때 커버리지는 2/3 = 66%이다.
     */
    @Test
    fun test() {
        val result = isStringLongBefore("abc")
        assertEquals(false, result)
    }

}