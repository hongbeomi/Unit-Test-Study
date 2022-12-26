package chapter10.step3_2

import chapter10.SqlConnection

abstract class IntegrationTests {

    companion object {
        private const val connectionString = "Server=.\\Sql;Database=IntegrationTests;Trusted_Connection=true"
    }

    init {
        clearDatabase()
    }

    private fun clearDatabase() {
        val query = """
            DELETE FROM dbo.[User];" 
            DELETE FROM dbo.Company;
        """.trimIndent()
        SqlConnection(connectionString).use {
            // 대충 쿼리 날리는 코드
            query
        }
    }

}