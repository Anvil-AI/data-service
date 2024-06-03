package ia.anvil.dataservice.repository

import ia.anvil.dataservice.data.Question
import org.springframework.data.mongodb.repository.MongoRepository

interface QuestionRepository : MongoRepository<Question, String> {
    fun findAllByUserId(userId: String): List<Question>
}