package ia.anvil.dataservice.controller

import ia.anvil.dataservice.data.Flashcard
import ia.anvil.dataservice.service.FlashcardService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.time.LocalDate

@RestController
@RequestMapping("/api/v1/flashcards")
class FlashcardController (val flashcardService: FlashcardService) {

    @GetMapping("/{themeId}")
    fun findAllByThemeId(@PathVariable categoryId: String): ResponseEntity<List<Flashcard>> {
        return ResponseEntity.ok(flashcardService.findAllByThemeId(categoryId).getOrNull())
    }

    @GetMapping("/daily/{timeReview}")
    fun findAllByDailyReview(@PathVariable timeReview: LocalDate): ResponseEntity<List<Flashcard>> {
        return ResponseEntity.ok(flashcardService.findAllByDailyReview(timeReview).getOrNull())
    }
}