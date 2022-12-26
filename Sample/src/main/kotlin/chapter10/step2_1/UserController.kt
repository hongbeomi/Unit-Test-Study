package chapter10.step2_1

import chapter10.SqlConnection
import chapter7.MessageBus
import chapter7.User
import chapter7.UserType
import chapter7.step2.UserFactory
import chapter7.step3.Company
import chapter7.step3.CompanyFactory
import java.io.Closeable

class UserController(
    transaction: Transaction,
    messageBus: MessageBus,
    domainLogger: IDomainLogger
) {

    private val _transaction: Transaction = transaction
    private val _userRepository: UserRepository
    private val _companyRepository: CompanyRepository
    private val _eventDispatcher: EventDispatcher

    init {
        _userRepository = UserRepository(transaction)
        _companyRepository = CompanyRepository(transaction)
        _eventDispatcher = EventDispatcher(messageBus, domainLogger)
    }

    fun changeEmail(userId: Int, newEmail: String): String {
        val userData = _userRepository.getUserById(userId)
        val user = UserFactory.create(userData)

        val error = user.canChangeEmail()

        if (error != null) return error

        val companyData = _companyRepository.getCompany()
        val company = CompanyFactory.create(companyData)

        user.changeEmailStep4(newEmail, company)

        _companyRepository.saveCompany(company)
        _userRepository.saveUser(user)

        // _eventDispatcher.dispatch(user.domainEvents)
        _transaction.commit()
        return "OK"
    }

}

class EventDispatcher(
    private val messageBus: MessageBus,
    private val domainLogger: ILogger
)

interface ILogger
class IDomainLogger : ILogger

class UserRepository(private val transaction: Transaction) {
    fun getUserById(userId: Int): List<Any> {
        SqlConnection(transaction.connectionString).use {
            val query = "SELECT * FROM [dbo].[User] WHERE UserID = $userId;"
            // 대충 쿼리 날리는 코드
            return listOf(
                userId, "dks3963@kakaostyle.com", UserType.Customer
            )
        }
    }

    fun saveUser(user: User) {
        SqlConnection(transaction.connectionString).use {
            val updateQuery = """UPDATE [dbo].[User] 
                SET Email = ${user.email}, Type = ${user.type}, IsEmailConfirmed = ${user.isEmailConfirmed}
                WHERE UserID = ${user.userId} 
                SELECT ${user.userId};""".trimIndent()

            val insertQuery = """INSERT [dbo].[User] (Email, Type, IsEmailConfirmed) 
                VALUES (${user.email}, ${user.type}, ${user.isEmailConfirmed})
                SELECT CAST(SCOPE_IDENTITY() as int);""".trimIndent()
            // 대충 쿼리 날리는 코드
        }
    }
}

class CompanyRepository(private val transaction: Transaction) {

    fun getCompany(): List<Any> {
        SqlConnection(transaction.connectionString).use {
            return listOf(
                Company.DOMAIN_NAME,
                0
            )
        }
    }

    fun saveCompany(company: Company) {
        SqlConnection(transaction.connectionString).use {
            val query = """UPDATE dbo.Company 
                SET DomainName = ${company.domainName},
                NumberOfEmployees = ${company.numberOfEmployees};""".trimIndent()
            // 대충 쿼리 날리는 코드
        }
    }

}

interface IDisposable: Closeable {
    fun dispose()

    override fun close() {
        dispose()
    }
}

class Transaction(
    val connectionString: String
) : IDisposable {

    private val scope = TransactionScope(connectionString)

    fun commit() {
        scope.complete()
    }

    override fun dispose() {
        scope.dispose()
    }

}

class TransactionScope(private val connectionString: String) {
    fun complete() {

    }

    fun dispose() {

    }
}