package ia.anvil.dataservice.repository

import ia.anvil.dataservice.data.User
import org.springframework.data.mongodb.repository.MongoRepository
import java.util.Optional
import java.util.UUID

interface UserRepository: MongoRepository<User, UUID> {

    fun findUserById(id: UUID): Optional<User>

    fun findUserByEmailAndPassword(email: String, password: String): Optional<User>

    fun findUserByPhone(phone: String): Optional<User>
}