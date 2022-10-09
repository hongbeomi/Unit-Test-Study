package chapter4.trivial_test

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

internal class UserTest {

    @Test
    fun test() {
        val sut = User("John Smith")

        assertEquals("John Smith", sut.name)
    }

}