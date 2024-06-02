package ia.anvil.dataservice.data

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class UserTest {

    @Test
    fun testUserCreation() {
        val userPreferences = UserPreferences(
            darkTheme = false,
            notifications = true,
            language = "English"
        )

        val user = User(
            email = "test@test.com",
            password = "password",
            phone = "1234567890",
            question = mutableListOf(),
            theme = emptyList(),
            preferences = userPreferences
        )

        Assertions.assertEquals("test@test.com", user.email)
        Assertions.assertEquals("password", user.password)
        Assertions.assertEquals("1234567890", user.phone)
        Assertions.assertTrue(user.question.isEmpty())
        Assertions.assertTrue(user.theme.isEmpty())
        Assertions.assertEquals(userPreferences, user.preferences)
    }
}