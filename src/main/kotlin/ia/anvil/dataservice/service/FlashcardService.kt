package ia.anvil.dataservice.service

import ia.anvil.dataservice.data.Flashcard
import ia.anvil.dataservice.repository.FlashcardRepository
import org.springframework.stereotype.Service
import java.time.LocalDate

@Service
class FlashcardService(val flashcardRepository: FlashcardRepository) {

    fun findAllByThemeId(themeId: String): Result<List<Flashcard>> = runCatching {
        flashcardRepository.findAllByThemeId(themeId)
    }

    fun findAllByDailyReview(timeReview: LocalDate): Result<List<Flashcard>> = runCatching {
        flashcardRepository.findAllByTimeReview(timeReview)
    }
}