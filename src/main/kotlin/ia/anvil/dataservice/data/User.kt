package ia.anvil.dataservice.data

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.util.UUID

@Document(collection = "user")
data class User(
    @Id
    val id: UUID,

    val email: String,
    val password: String,
    val phone: String,

    val theme: List<Theme>,
    val preferences: UserPreferences
) {
    constructor (dto: UserAuthenticationDto): this(
        UUID.randomUUID(), dto.email, dto.password, dto.phone,
        emptyList(),
        UserPreferences(true, true, "PT-BR")
    )
}

data class UserPreferences(
    val darkTheme: Boolean,

    val notifications: Boolean,
    val language: String,
)

data class UserAuthenticationDto(
    var email: String,
    var password: String,
    val phone: String,
)