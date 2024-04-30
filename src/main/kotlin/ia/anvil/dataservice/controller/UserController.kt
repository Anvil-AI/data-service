package ia.anvil.dataservice.controller

import ia.anvil.dataservice.configuration.AuthConfiguration
import ia.anvil.dataservice.data.User
import ia.anvil.dataservice.data.UserAuthenticationDto
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
    @PostMapping("/generate-question")
    fun generateQuestion(@RequestParam subject: String, @RequestParam difficulty: String): ResponseEntity<Any> {
        userService.generateQuestion(subject, difficulty)
        return ResponseEntity.ok().body(mapOf("message" to "Pergunta gerada"))
    }

    @GetMapping("/get-question")
    fun getQuestion(): ResponseEntity<Any> {
        val question = userService.getQuestion()
        return ResponseEntity.ok().body(mapOf("question" to question))
    }

    @Transactional
    fun saveUser(user: UserAuthenticationDto): String {
        user.password = authConfiguration.bCryptPasswordEncoder().encode(user.password)
        return userService.saveUser(user).toString()
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

}