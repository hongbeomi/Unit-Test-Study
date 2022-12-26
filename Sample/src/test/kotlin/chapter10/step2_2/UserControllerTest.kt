package chapter10.step2_2

import chapter10.step2_2.CompanyRepository
import chapter10.step2_2.CrmContext
import chapter10.step2_2.IDomainLogger
import chapter10.step2_2.UserController
import chapter10.step2_2.UserRepository
import chapter7.MessageBus
import chapter7.User
import chapter7.UserType
import chapter7.step3.Company
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class UserControllerTest {

    companion object {
        private const val connectionString = "Server=.\\Sql;Database=IntegrationTests;Trusted_Connection=true"
    }

    @Test
    fun changing_email_from_corporate_to_non_corporate() {
        val context = CrmContext(connectionString)
        context.use {
            // 준비 구절에서 context 사용
            val userRepository = UserRepository(it)
            val companyRepository = CompanyRepository(it)
            val user = User(
                userId = 0,
                email = "dks3963@kakaostyle.com",
                type = UserType.Employee,
                isEmailConfirmed = false
            )
            userRepository.saveUser(user)
            val company = Company("domain", 1)
            companyRepository.saveCompany(company)
            context.saveChanges()

//            val busSpy = BusSpy()
//            val messageBus = MessageBus(busSpy)
//            val loggerMock = Mock<IDomainLogger>()

            // 실행 구절에서 또 사용
            val sut = UserController(
                context,
                MessageBus,
                IDomainLogger()
            )

            // 실행
            val result = sut.changeEmail(user.userId, "new@kakaostyle.com")

            // 검증
            assertEquals("OK", result)

            // 또 검증문에서 사용
            val userFromDb = userRepository.getUserById(user.userId)

            assertEquals("new@kakaostyle.com", userFromDb.email)
            assert(userFromDb.type == UserType.Customer)

            // 또 검증문에서 사용
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


}