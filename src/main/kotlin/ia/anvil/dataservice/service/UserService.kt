package ia.anvil.dataservice.service

import ia.anvil.dataservice.data.User
import ia.anvil.dataservice.repository.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.util.*

@Service
class UserService(val userRepository: UserRepository) {

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
}