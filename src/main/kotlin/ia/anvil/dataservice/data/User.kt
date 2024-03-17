package ia.anvil.dataservice.data

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.util.UUID

@Document
data class User(
    @Id
    val id: UUID,

    val email: String,
    val password: String,
    val phone: String,

    val theme: List<Theme>,
    val preferences: UserPreferences
)

data class UserPreferences(
    val darkTheme: Boolean,

    val notifications: Boolean,
    val language: String,
)