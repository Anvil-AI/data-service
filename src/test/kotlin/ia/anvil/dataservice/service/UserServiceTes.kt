package ia.anvil.dataservice.service

import ia.anvil.dataservice.data.User
import ia.anvil.dataservice.data.UserAnswerDto
import ia.anvil.dataservice.data.UserAuthenticationDto
import ia.anvil.dataservice.data.UserPreferences
import ia.anvil.dataservice.repository.UserRepository
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import java.util.Optional

class UserServiceTest {

    private val userRepository = Mockito.mock(UserRepository::class.java)
    private val learningService = Mockito.mock(LearningService::class.java)
    private val userService = UserService(userRepository, learningService)

    @Test
    fun testCheckAnswer() {
        val userAnswerDto = UserAnswerDto("question", "answer")
        val result = userService.checkAnswer(userAnswerDto)
        Assertions.assertEquals(false, result)
    }

    @Test
fun testSaveUser() {
    val userAuthenticationDto = UserAuthenticationDto(
        email = "test@test.com",
        password = "password",
        phone = "1234567890",
        question = mutableListOf(),
        theme = emptyList(),
        preferences = UserPreferences(
            darkTheme = false,
            notifications = true,
            language = "English"
        )
    )

    val expectedUser = User(userAuthenticationDto)
    val dummyUser = User(userAuthenticationDto.copy(email = "dummy@test.com")) // create a dummy user

    Mockito.`when`(userRepository.save(dummyUser)).thenReturn(Optional.of(expectedUser))

    val result = userService.saveUser(userAuthenticationDto)

    Assertions.assertEquals(expectedUser.id, result.getOrNull())
}
}