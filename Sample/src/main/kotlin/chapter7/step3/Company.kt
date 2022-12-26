package chapter7.step3

data class Company(
    val domainName: String,
    var numberOfEmployees: Int
) {

    companion object {
        const val DOMAIN_NAME = "kakaostyle"
        const val EMAIL_DELIMITER = "@"
        const val EMAIL_SUFFIX = ".com"
    }

    fun changeNumberOfEmployees(delta: Int) {
        require(numberOfEmployees + delta >= 0)

        numberOfEmployees += delta
    }

    fun isEmailCorporate(email: String): Boolean {
        val emailDomain = email.split(EMAIL_DELIMITER).last()
        return emailDomain == domainName
    }

}