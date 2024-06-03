package ia.anvil.dataservice.data

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.util.UUID

@Document(collection = "question")
data class Question(
    @Id
    val id: UUID = UUID.randomUUID(),
    val userId: String,
    val question: String,
    val answer: String
)