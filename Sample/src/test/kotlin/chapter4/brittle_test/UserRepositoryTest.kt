package chapter4.brittle_test

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

internal class UserRepositoryTest {

    @Test
    fun test() {
        val sut = UserRepository()

        sut.getById(5)

        assertEquals(
            "SELECT * FROM dbo.[User] WHERE UserID = 5",
            sut.lastExecutedSqlStatement
        )
    }

}