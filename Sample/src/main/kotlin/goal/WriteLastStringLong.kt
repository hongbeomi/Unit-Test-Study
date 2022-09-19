package goal

var wasLastStringLong: Boolean = false
    private set

fun writeLastIsStringLong(input: String): Boolean {
    val result = input.length > 5
    wasLastStringLong = result
    return result
}
