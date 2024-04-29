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

    fun create(themeId: String, flashcard: Flashcard): Result<Flashcard> = runCatching {
        if(flashcard.getQuestion().isEmpty()) //excecao
        if(flashcard.getAnswer().isEmpty()) //excecao

        flashcardRepository.save(flashcard)
        flashcard
    }

    fun update(flashcardId: String, flashcard: Flashcard): Result<Flashcard> = runCatching {
        if(flashcardRepository.findById(flashcardId).isEmpty)
            throw NoSuchElementException("Flashcard com ID $flashcardId não encontrado")

        flashcardRepository.save(flashcard)
        flashcard
    }

    fun delete(flashcardId: String): Result<Boolean> = runCatching {
        if(flashcardRepository.findById(flashcardId).isEmpty)
            throw NoSuchElementException("Flashcard com ID $flashcardId não encontrado")
        flashcardRepository.deleteById(flashcardId)
        true
    }
}