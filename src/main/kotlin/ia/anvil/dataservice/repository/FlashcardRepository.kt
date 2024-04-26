package ia.anvil.dataservice.repository

import ia.anvil.dataservice.data.Flashcard
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository
import java.time.LocalDate

@Repository
interface FlashcardRepository: MongoRepository<Flashcard, String> {

    fun findAllByThemeId(themeId:String): List<Flashcard>

    fun findAllByTimeReview(timeReview: LocalDate): List<Flashcard>
}