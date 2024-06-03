package ia.anvil.dataservice.data

import lombok.Generated
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.util.UUID

@Document(collection = "user")
data class User(
    @Id
    val id: UUID = UUID.randomUUID(),

    val email: String,
    val password: String,
    val phone: String,

    val questions: MutableList<Question> = mutableListOf(), // Adicionado campo questions

    val theme: List<Theme>,
    val preferences: UserPreferences

) {
    constructor (dto: UserAuthenticationDto): this(
        UUID.randomUUID(), dto.email, dto.password, dto.phone,
        mutableListOf(),
        dto.theme,
        dto.preferences
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
    val question: MutableList<String> = mutableListOf(),
    val theme: List<Theme>,
    val preferences: UserPreferences

)