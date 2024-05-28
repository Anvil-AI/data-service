package ia.anvil.dataservice.service

import ia.anvil.dataservice.data.UserAnswerDto
import ia.anvil.dataservice.repository.UserRepository
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.mockito.Mockito

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
}