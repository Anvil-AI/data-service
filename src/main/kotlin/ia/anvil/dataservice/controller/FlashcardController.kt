package ia.anvil.dataservice.controller

import ia.anvil.dataservice.data.Flashcard
import ia.anvil.dataservice.service.FlashcardService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
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

    @PostMapping("/{themeId}")
    fun create(@PathVariable themeId:String, @RequestBody request: Flashcard): ResponseEntity<Flashcard>{
        return flashcardService.create(themeId,request).fold(
            onSuccess = { ResponseEntity.ok(it)},
            onFailure = { ResponseEntity.badRequest().build() }
        )
    }

    @PutMapping("/{flashcardId}")
    fun update(@PathVariable flashcardId: String, @RequestBody request: Flashcard): ResponseEntity<Flashcard>{
        return flashcardService.update(flashcardId, request).fold(
            onSuccess = { ResponseEntity.ok(it)},
            onFailure = { ResponseEntity.notFound().build()}
        )
    }

    @DeleteMapping("/{flashcardId}")
    fun delete(@PathVariable flashcardId: String): ResponseEntity<Boolean>{
        return flashcardService.delete(flashcardId).fold(
            onSuccess = { ResponseEntity.ok().build<Boolean>() },
            onFailure = { ResponseEntity.notFound().build() }
        )
    }
}