package ia.anvil.dataservice.service

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class LearningServiceTest {

    private val learningService = LearningService()

    @Test
    fun testGenerateQuestion() {
        val learningRequest = LearningRequestDto("subject", "EASY")
        learningService.generateQuestion(learningRequest)
        val question = learningService.getQuestion()
        Assertions.assertNotNull(question)
    }
}