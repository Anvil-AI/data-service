package ia.anvil.dataservice.data

import org.springframework.data.mongodb.core.mapping.Document

@Document
data class Theme(
    val name: String,
    val successRate: Float,
)