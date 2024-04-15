package ia.anvil.dataservice.data

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.time.LocalDate

@Document(collection = "flashcard")
data class Flashcard(
    @Id
    private val id: String,
    private var question: String,
    private var answer: String,
    private var theme: String,
    private var Difficulty: Difficulty = ia.anvil.dataservice.data.Difficulty.BEGIN,
    private var timeSkip: LocalDate = LocalDate.now(),
    private var numberReview: Int = 0,
    private var themeId: String,
){
    init {
        require(question.isNotEmpty()) {"Question cannot be empty."}
        require(answer.isNotEmpty()) {"Answer cannot be empty."}
    }
}
