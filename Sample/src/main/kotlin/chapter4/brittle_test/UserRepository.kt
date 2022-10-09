package chapter4.brittle_test

import chapter4.trivial_test.User
import java.lang.IllegalStateException

class UserRepository {

    var lastExecutedSqlStatement: String = ""
        get() = field.ifEmpty {
            throw IllegalStateException("Nothing has been execute")
        }
        private set


    fun getById(id: Int): User {
        lastExecutedSqlStatement = "SELECT * FROM dbo.[User] WHERE UserID = $id"
        return User("tester")
    }

}