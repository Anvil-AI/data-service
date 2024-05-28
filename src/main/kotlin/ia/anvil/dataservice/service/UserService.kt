package ia.anvil.dataservice.service

import ia.anvil.dataservice.data.QuestionRequestDto
import ia.anvil.dataservice.data.User
import ia.anvil.dataservice.data.UserAuthenticationDto
import ia.anvil.dataservice.data.UserAnswerDto
import ia.anvil.dataservice.repository.UserRepository
import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.client.WebClient
import java.util.*
import java.io.FileInputStream
import java.util.Properties
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.http.HttpEntity
import org.springframework.web.client.RestTemplate

@Service
class UserService(val userRepository: UserRepository,val learningService: LearningService) {

    private var generatedQuestion: String? = null
    private val webClient: WebClient = WebClient.create()

    fun generateQuestion(questionRequest: QuestionRequestDto): UserAnswerDto {
        val learningRequest = LearningRequestDto(questionRequest.subject, questionRequest.difficulty)
        val generatedQuestion = learningService.generateQuestion(learningRequest).toString()
        return UserAnswerDto(generatedQuestion, "")
    }

    fun checkAnswer(userAnswerDto: UserAnswerDto): Boolean {
        val gpt3ApiUrl = "https://api.openai.com/v1/engines/davinci-codex/completions"
        val prompt = userAnswerDto.question
        val maxTokens = 60

        val headers = HttpHeaders()
        headers.contentType = MediaType.APPLICATION_JSON
        val apiKeys = loadApiKeys()
        headers.set("Authorization", "Bearer " + apiKeys.getProperty("openai.api.key"))

        val body = mapOf("prompt" to prompt, "max_tokens" to maxTokens)
        val request = HttpEntity(body, headers)

        val restTemplate = RestTemplate()
        try {
            val correctAnswer = restTemplate.postForObject(gpt3ApiUrl, request, String::class.java)
            return userAnswerDto.userAnswer == correctAnswer
        } catch (e: Exception) {
            println("Erro ao fazer a chamada de API: ${e.message}")
            return false
        }
    }
    fun getQuestion(): String {
        return generatedQuestion ?: "Nenhuma pergunta foi gerada ainda"
    }

    fun generateAndSaveQuestion(user: User){
    val difficulties = listOf("EASY", "MEDIUM", "HARD")

        difficulties.forEach { difficulty ->
        repeat(3){
            val questionRequest = QuestionRequestDto("subject", difficulty)
            generateQuestion(questionRequest)
            val question = generatedQuestion ?: "Nenhuma pergunta foi gerada ainda"
            user.question.add(question)
        }
        }
        userRepository.save(user)
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
        val user = User(userAuthenticationDto)
        val savedUser = userRepository.save(user)
            .orElseThrow { Exception("Could not save user") }

        return Result.success(savedUser.id)
    }
    fun loadApiKeys(): Properties {
        val properties = Properties()
        val inputStream = FileInputStream("application-secrets.properties")
        properties.load(inputStream)
        return properties
    }
}