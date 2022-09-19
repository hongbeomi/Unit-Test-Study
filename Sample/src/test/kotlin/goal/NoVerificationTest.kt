package goal

import org.junit.jupiter.api.Test

class NoVerificationTest {

    /**
     * @see isStringLongAfter
     * 이 테스트는 코드, 분기 커버리지가 모두 100%이지만 쓸모가 없다.
     */
    @Test
    fun test() {
        val result1 = isStringLongAfter("abc")
        val result2 = isStringLongAfter("abcdef")
    }

}