package chapter7.step2

import chapter7.User
import chapter7.UserType

class UserFactory {

    companion object {
        fun create(data: List<Any>): User {
            require(data.size >= 3)

            return data.run {
                User(
                    userId = get(0) as Int,
                    email = get(1) as String,
                    type = get(2) as UserType,
                    isEmailConfirmed = false,
                )
            }
        }
    }

}