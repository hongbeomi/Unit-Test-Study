package chapter10.step2_2

import chapter7.MessageBus
import chapter7.User
import chapter7.UserType
import chapter7.step3.Company
import java.io.Closeable

class UserController(
    context: CrmContext,
    messageBus: MessageBus,
    domainLogger: IDomainLogger
) {

    private val _context: CrmContext
    private val _userRepository: UserRepository
    private val _companyRepository: CompanyRepository
    private val _eventDispatcher: EventDispatcher

    init {
        _context = context
        _userRepository = UserRepository(context)
        _companyRepository = CompanyRepository(context)
        _eventDispatcher = EventDispatcher(messageBus, domainLogger)
    }

    fun changeEmail(userId: Int, newEmail: String): String {
        val user = _userRepository.getUserById(userId)

        val error = user?.canChangeEmail()

        if (error != null) return error

        val company = _companyRepository.getCompany()

        user?.changeEmailStep4(newEmail, company)

        _companyRepository.saveCompany(company)
        _userRepository.saveUser(user)

        // _eventDispatcher.dispatch(user.domainEvents)
        _context.saveChanges()
        return "OK"
    }

}

class EventDispatcher(
    private val messageBus: MessageBus,
    private val domainLogger: ILogger
)

interface ILogger
class IDomainLogger : ILogger

class UserRepository(private val context: CrmContext) {
    fun getUserById(userId: Int): User? = context.users.find { it.userId == userId }

    fun saveUser(user: User?) = user?.let { context.users.add(it) }
}

class CompanyRepository(private val context: CrmContext) {

    fun getCompany(): Company = context.company

    fun saveCompany(company: Company) {
        context.company = company
    }

}

class CrmContext(connectionString: String) : Closeable {
    val users = mutableListOf(
        User(
            userId = 1,
            email = "email",
            type = UserType.Customer,
            isEmailConfirmed = false
        )
    )
    var company = Company("domain", 1)

    override fun close() {

    }

    fun saveChanges() {}

}