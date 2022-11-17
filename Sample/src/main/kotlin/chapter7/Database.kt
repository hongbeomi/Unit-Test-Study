package chapter7

import chapter7.step3.Company
import chapter7.step3.Company.Companion.DOMAIN_NAME
import chapter7.step3.Company.Companion.EMAIL_DELIMITER
import chapter7.step3.Company.Companion.EMAIL_SUFFIX


object Database {

    private var numberOfEmployees = 0

    fun getUserById(userId: Int): List<Any> = listOf(
        userId,
        "$userId$EMAIL_DELIMITER$DOMAIN_NAME$EMAIL_SUFFIX",
        UserType.values().random()
    )

    fun getCompany(): List<Any> = listOf(DOMAIN_NAME, numberOfEmployees)

    fun saveCompany(newNumber: Int) {
        numberOfEmployees = newNumber
    }

    fun saveCompany(company: Company) {
        println("save $company")
    }

    fun saveUser(user: User) {
        println("save $user")
    }

}