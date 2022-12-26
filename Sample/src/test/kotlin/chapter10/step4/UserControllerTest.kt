package chapter10.step4

import chapter10.step2_2.*
import chapter7.MessageBus
import chapter7.User
import chapter7.UserType
import chapter7.step3.Company
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

class UserControllerTest {

    companion object {
        private const val connectionString = "Server=.\\Sql;Database=IntegrationTests;Trusted_Connection=true"
    }

    @Test
    fun changing_email_from_corporate_to_non_corporate_before() {
        // 준비
        val user: User
        CrmContext(connectionString).use {
            val userRepository = UserRepository(it)
            val companyRepository = CompanyRepository(it)

            user = User(
                userId = 0,
                email = "dks3963@kakaostyle.com",
                type = UserType.Employee,
                isEmailConfirmed = false
            )
            userRepository.saveUser(user)

            val company = Company("domain", 1)
            companyRepository.saveCompany(company)

            it.saveChanges()
        }

//        val busSpy = BusSpy()
//        val messageBus = MessageBus(busSpy)
//        val loggerMock = Mock<IDomainLogger>()

        val result: String
        CrmContext(connectionString).use {
            val sut = UserController(
                context = it,
                messageBus = MessageBus,
                domainLogger = IDomainLogger()
            )
            // 실행
            result = sut.changeEmail(user.userId, "new@kakaostyle.com")
        }

        // 검증
        assertEquals("OK", result)

        CrmContext(connectionString).use {
            val userRepository = UserRepository(it)
            val companyRepository = CompanyRepository(it)

            val userFromDb = userRepository.getUserById(user.userId)
            assertEquals("new@kakaostyle.com", userFromDb?.email)
            assert(userFromDb?.type == UserType.Customer)

            val companyFromDb = companyRepository.getCompany()
            assertEquals(0, companyFromDb.numberOfEmployees)

            /**
            busSpy.shouldSendNumberOfMessagges(1).withEmailChangeMessage(user.userId, "new@kakaostyle.com")
            loggerMock.verify(
            x -> x.userTypeHasChanged(user.userId, UserType.Employee, UserType.Customer),
            Times.Once
            )
             **/
        }
    }

    // 비공개 팩토리 메서드 도입 (오브젝트 마더)
    // 팩토리 메서드를 동일한 클래스에 배치하라. 코드 복제가 중요한 문제가 될 경우에만 별도의 헬퍼 클래스로 이동하라.
    private fun createUser(
        email: String = "user@kakaostyle.com",
        type: UserType = UserType.Employee,
        isEmailConfirmed: Boolean = false
    ): User {
        CrmContext(connectionString).use {
            val user = User(0, email, type, isEmailConfirmed)
            val repository = UserRepository(it)
            repository.saveUser(user)

            it.saveChanges()
            return user
        }
    }

    private fun createCompany(
        domainName: String,
        numberOfEmployees: Int
    ) {
        CrmContext(connectionString).use {
            val company = Company(domainName, numberOfEmployees)
            val repository = CompanyRepository(it)
            repository.saveCompany(company)
            it.saveChanges()
        }
    }

    @Test
    fun changing_email_from_corporate_to_non_corporate_after_준비() {
        // 준비
        val user: User = createUser(email = "dks3963@kakaostyle.com")
        createCompany("domain", 1)

//        val busSpy = BusSpy()
//        val messageBus = MessageBus(busSpy)
//        val loggerMock = Mock<IDomainLogger>()

        val result: String
        CrmContext(connectionString).use {
            val sut = UserController(
                context = it,
                messageBus = MessageBus,
                domainLogger = IDomainLogger()
            )
            // 실행
            result = sut.changeEmail(user.userId, "new@kakaostyle.com")
        }

        // 검증
        assertEquals("OK", result)

        CrmContext(connectionString).use {
            val userRepository = UserRepository(it)
            val companyRepository = CompanyRepository(it)

            val userFromDb = userRepository.getUserById(user.userId)
            assertEquals("new@kakaostyle.com", userFromDb?.email)
            assert(userFromDb?.type == UserType.Customer)

            val companyFromDb = companyRepository.getCompany()
            assertEquals(0, companyFromDb.numberOfEmployees)

            /**
            busSpy.shouldSendNumberOfMessagges(1).withEmailChangeMessage(user.userId, "new@kakaostyle.com")
            loggerMock.verify(
            x -> x.userTypeHasChanged(user.userId, UserType.Employee, UserType.Customer),
            Times.Once
            )
             **/
        }
    }

    private fun execute(
        messageBus: MessageBus,
        logger: IDomainLogger,
        func: (UserController) -> String,
    ): String {
        CrmContext(connectionString).use {
            val controller = UserController(it, messageBus, logger)
            return func.invoke(controller)
        }
    }

    @Test
    fun changing_email_from_corporate_to_non_corporate_after_실행() {
        // 준비
        val user: User = createUser(email = "dks3963@kakaostyle.com")
        createCompany("domain", 1)

//        val busSpy = BusSpy()
//        val messageBus = MessageBus(busSpy)
//        val loggerMock = Mock<IDomainLogger>()

        // 실행
        val result = execute(
            messageBus = MessageBus,
            logger = IDomainLogger()
        ) {
            it.changeEmail(user.userId, "new@kakaostyle.com")
        }

        // 검증
        assertEquals("OK", result)

        CrmContext(connectionString).use {
            val userRepository = UserRepository(it)
            val companyRepository = CompanyRepository(it)

            val userFromDb = userRepository.getUserById(user.userId)
            assertEquals("new@kakaostyle.com", userFromDb?.email)
            assert(userFromDb?.type == UserType.Customer)

            val companyFromDb = companyRepository.getCompany()
            assertEquals(0, companyFromDb.numberOfEmployees)

            /**
            busSpy.shouldSendNumberOfMessagges(1).withEmailChangeMessage(user.userId, "new@kakaostyle.com")
            loggerMock.verify(
            x -> x.userTypeHasChanged(user.userId, UserType.Employee, UserType.Customer),
            Times.Once
            )
             **/
        }
    }

    private fun queryUser(userId: Int): User? {
        CrmContext(connectionString).use {
            val userRepository = UserRepository(it)
            return userRepository.getUserById(userId)
        }
    }

    private fun queryCompany(): Company {
        CrmContext(connectionString).use {
            val companyRepository = CompanyRepository(it)
            return companyRepository.getCompany()
        }
    }

    @Test
    fun changing_email_from_corporate_to_non_corporate_after_검증() {
        // 준비
        val user: User = createUser(email = "dks3963@kakaostyle.com")
        createCompany("domain", 1)

//        val busSpy = BusSpy()
//        val messageBus = MessageBus(busSpy)
//        val loggerMock = Mock<IDomainLogger>()

        // 실행
        val result = execute(
            messageBus = MessageBus,
            logger = IDomainLogger()
        ) {
            it.changeEmail(user.userId, "new@kakaostyle.com")
        }

        // 검증
        assertEquals("OK", result)

        val userFromDb = queryUser(user.userId)
        assertEquals("new@kakaostyle.com", userFromDb?.email)
        assert(userFromDb?.type == UserType.Customer)

        val companyFromDb = queryCompany()
        assertEquals(0, companyFromDb.numberOfEmployees)

        /**
        busSpy.shouldSendNumberOfMessagges(1).withEmailChangeMessage(user.userId, "new@kakaostyle.com")
        loggerMock.verify(
        x -> x.userTypeHasChanged(user.userId, UserType.Employee, UserType.Customer),
        Times.Once
        )
         **/
    }

    // 데이터 검증을 위한 플루언트 인터페이스
    private fun User?.shouldExist(): User {
        assertNotNull(this)
        return this
    }

    private fun User.withEmail(email: String) = apply {
        assertEquals(email, this.email)
    }

    private fun User.withType(userType: UserType) = apply {
        assertEquals(userType, this.type)
    }

    private fun Company?.shouldExist(): Company {
        assertNotNull(this)
        return this
    }

    private fun Company.withNumberOfEmployees(numberOfEmployees: Int) = apply {
        assertEquals(numberOfEmployees, this.numberOfEmployees)
    }

    @Test
    fun changing_email_from_corporate_to_non_corporate_after_플루언트_인터페이스() {
        // 준비
        val user: User = createUser(email = "dks3963@kakaostyle.com")
        createCompany("domain", 1)

//        val busSpy = BusSpy()
//        val messageBus = MessageBus(busSpy)
//        val loggerMock = Mock<IDomainLogger>()

        // 실행
        val result = execute(
            messageBus = MessageBus,
            logger = IDomainLogger()
        ) {
            it.changeEmail(user.userId, "new@kakaostyle.com")
        }

        // 검증
        assertEquals("OK", result)

        val userFromDb = queryUser(user.userId)
        userFromDb
            .shouldExist()
            .withEmail("new@kakaostyle.com")
            .withType(UserType.Customer)

        val companyFromDb = queryCompany()
        companyFromDb
            .shouldExist()
            .withNumberOfEmployees(0)

        /**
        busSpy.shouldSendNumberOfMessagges(1).withEmailChangeMessage(user.userId, "new@kakaostyle.com")
        loggerMock.verify(
        x -> x.userTypeHasChanged(user.userId, UserType.Employee, UserType.Customer),
        Times.Once
        )
         **/
    }

}