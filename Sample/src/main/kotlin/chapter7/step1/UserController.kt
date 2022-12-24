package chapter7.step1

import chapter7.Database
import chapter7.MessageBus
import chapter7.User
import chapter7.UserType
import chapter7.step2.UserFactory
import chapter7.step3.CompanyFactory

class UserController {

    private val _database = Database
    private val _messageBus = MessageBus

    fun changeEmail(userId: Int, newEmail: String) {
        val data = _database.getUserById(userId)
        val email = data[1] as String
        val type = data[2] as UserType

        val user = User(userId, email, type, false)

        val companyData = _database.getCompany()
        val companyDomainName = companyData[0] as String
        val numberOfEmployees = companyData[1] as Int

        val newNumberOfEmployees = user.changeEmailStep1(
            newEmail, companyDomainName, numberOfEmployees
        )

        _database.saveCompany(newNumberOfEmployees)
        _database.saveUser(user)
        _messageBus.sendEmailChangedMessage(userId, newEmail)
    }

    fun changeEmailStep2(userId: Int, newEmail: String) {
        val userData = _database.getUserById(userId)

        val user = UserFactory.create(userData)

        val companyData = _database.getCompany()
        val companyDomainName = companyData[0] as String
        val numberOfEmployees = companyData[1] as Int

        val newNumberOfEmployees = user.changeEmailStep1(
            newEmail, companyDomainName, numberOfEmployees
        )

        _database.saveCompany(newNumberOfEmployees)
        _database.saveUser(user)
        _messageBus.sendEmailChangedMessage(userId, newEmail)
    }

    fun changeEmailStep3(userId: Int, newEmail: String) {
        val userData = _database.getUserById(userId)

        val user = UserFactory.create(userData)

        val companyData = _database.getCompany()
        val company = CompanyFactory.create(companyData)

        user.changeEmailStep3(newEmail, company)

        _database.saveCompany(company)
        _database.saveUser(user)
        _messageBus.sendEmailChangedMessage(userId, newEmail)
    }

    fun changeEmailStep4(userId: Int, newEmail: String) {
        val userData = _database.getUserById(userId)

        val user = UserFactory.create(userData)

        val companyData = _database.getCompany()
        val company = CompanyFactory.create(companyData)

        user.changeEmailStep4(newEmail, company)

        _database.saveCompany(company)
        _database.saveUser(user)
        _messageBus.sendEmailChangedMessage(userId, newEmail)
    }

    fun changeEmailStep5(userId: Int, newEmail: String) {
        val userData = _database.getUserById(userId)

        val user = UserFactory.create(userData)

        val companyData = _database.getCompany()
        val company = CompanyFactory.create(companyData)

        user.changeEmailStep5(newEmail, company)

        _database.saveCompany(company)
        _database.saveUser(user)

        user.emailChangedEvents.forEach {
            _messageBus.sendEmailChangedMessage(it.userId, it.newEmail)
        }
    }

}