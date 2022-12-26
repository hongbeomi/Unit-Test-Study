package chapter10

import chapter7.User
import chapter7.step3.Company
import java.io.Closeable

class Database(
    private val connectionString: String
) {

    fun saveUser(user: User) {
        val isNewUser = user.userId == 0
        val connection = SqlConnection(connectionString)
        connection.use {
            // isNewUser에 따라 사용자 생성 또는 없데이트
        }
    }

    fun saveCompany(company: Company) {
        val connection = SqlConnection(connectionString)
        connection.use {
            // 회사는 하나만 있기 때문에 업데이트만 함.
        }
    }

}

class SqlConnection(private val connectionString: String) : Closeable {
    override fun close() {
        // no-op
    }
}