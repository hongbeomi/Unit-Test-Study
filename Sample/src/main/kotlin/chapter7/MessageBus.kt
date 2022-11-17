package chapter7

object MessageBus {
    fun sendEmailChangedMessage(userId: Int, newEmail: String) {
        println("User ID : $userId\nnewEmail : $newEmail")
    }
}