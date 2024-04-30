package ia.anvil.dataservice.service

import ia.anvil.dataservice.data.User
import ia.anvil.dataservice.data.UserAuthenticationDto
import ia.anvil.dataservice.repository.UserRepository
import org.springframework.stereotype.Service
import java.util.*

@Service
class UserService(val userRepository: UserRepository,val learningService: LearningService) {

        private var generatedQuestion: String? = null

    fun generateQuestion(subject: String, difficulty: String) {
        val learningRequest = LearningRequestDto(subject, difficulty)

        // Use o LearningService para gerar a pergunta
        learningService.generateQuestion(learningRequest)
    }

    fun getQuestion(): String {
        return generatedQuestion ?: "Nenhuma pergunta foi gerada ainda"
    }

    fun findUserById(id: String): Result<User> = runCatching {
        userRepository.findUserById(UUID.fromString(id))
            .orElseThrow { Exception("User not found") }
    }

    fun findUserByEmailAndPassword(email: String, password: String): Result<User> = runCatching {
        userRepository.findUserByEmailAndPassword(email, password)
            .orElseThrow { Exception("User not found") }
    }

    fun findUserByPhone(phone: String): Result<User> = runCatching {
        userRepository.findUserByPhone(phone)
            .orElseThrow { Exception("User not found") }
    }

    fun saveUser(userAuthenticationDto: UserAuthenticationDto): Result<UUID> = runCatching {
        val orElseThrow = userRepository.save(User(userAuthenticationDto))
            .orElseThrow { Exception("Could not save user") }

        return Result.success(orElseThrow.id)
    }
}