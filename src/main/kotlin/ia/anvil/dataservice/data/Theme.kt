package ia.anvil.dataservice.data

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document(collection = "themes")
data class Theme(
    @Id
    private val id: String,
    private var name: String,
    private var successRate: Float,
    private var length: Int,
    private val userId: String


)