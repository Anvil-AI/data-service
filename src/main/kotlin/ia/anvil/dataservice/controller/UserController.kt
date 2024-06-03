package ia.anvil.dataservice.controller


import ia.anvil.dataservice.configuration.AuthConfiguration
import ia.anvil.dataservice.data.QuestionRequestDto
import ia.anvil.dataservice.data.User
import ia.anvil.dataservice.data.UserAuthenticationDto
import ia.anvil.dataservice.data.UserAnswerDto
import ia.anvil.dataservice.service.UserService
import org.springframework.http.ResponseEntity
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1/users")
class UserController(
    val userService: UserService,
    val authConfiguration: AuthConfiguration,

) {
    @PostMapping("/register")
    fun registerUser(@RequestBody user: UserAuthenticationDto, @RequestBody questionRequest: QuestionRequestDto): ResponseEntity<String> {
    val savedUserId = saveUser(user, questionRequest)
    return ResponseEntity.ok(savedUserId)
}

    @GetMapping("/get-question")
    fun getQuestion(): ResponseEntity<Any> {
        val question = userService.getQuestion()
        return ResponseEntity.ok().body(mapOf("question" to question))
    }

    @Transactional
    fun saveUser(user: UserAuthenticationDto, questionRequest: QuestionRequestDto): String {
    user.password = authConfiguration.bCryptPasswordEncoder().encode(user.password)
    val savedUserId = userService.saveUser(user).getOrNull()
    val savedUser = userService.findUserById(savedUserId.toString()).getOrNull()
    if (savedUser != null) {
        userService.generateAndSaveQuestion(savedUser, questionRequest)
    }
    return savedUserId.toString()
}

    @GetMapping("/{id}")
    fun findUserById(@PathVariable id: String): ResponseEntity<User> {
        val user = userService.findUserById(id)
        if (user.isFailure) return ResponseEntity.notFound().build()

        return ResponseEntity.ok(user.getOrNull())
    }

    @GetMapping("/{email}/{password}")
    fun findUserByEmailAndPassword(@PathVariable email: String, @PathVariable password: String): ResponseEntity<User> {
        val user = userService.findUserByEmailAndPassword(email, password)
        if (user.isFailure) return ResponseEntity.notFound().build()

        return ResponseEntity.ok(user.getOrNull())
    }

    @GetMapping("/{phone}")
    fun findUserByPhone(@PathVariable phone: String): ResponseEntity<User> {
        val user = userService.findUserByPhone(phone)
        if (user.isFailure) return ResponseEntity.notFound().build()

        return ResponseEntity.ok(user.getOrNull())
    }
    @PostMapping("/submit-answer")
    fun submitAnswer(@RequestBody userAnswerDto: UserAnswerDto): ResponseEntity<Boolean> {
        val isCorrect = userService.checkAnswer(userAnswerDto)
        return ResponseEntity.ok(isCorrect)
    }
    @PostMapping("/{userId}/generateQuestion")
    fun generateQuestion(@PathVariable userId: String, @RequestBody questionRequest: QuestionRequestDto): ResponseEntity<UserAnswerDto> {
    val userResult = userService.findUserById(userId)
    if (userResult.isFailure) return ResponseEntity.notFound().build()

    val user = userResult.getOrNull()
    var userAnswerDto: UserAnswerDto? = null
    if (user != null) {
        userAnswerDto = userService.generateAndSaveQuestion(user, questionRequest)
    }
    return ResponseEntity.ok(userAnswerDto)
    }


}