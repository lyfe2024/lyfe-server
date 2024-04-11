package lyfe.lyfeBe.web.whisky

import lyfe.lyfeBe.dto.CommonResponse
import lyfe.lyfeBe.whisky.WhiskyCreate
import lyfe.lyfeBe.whisky.WhiskyService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/v1")
class WhiskyController(
    private val service: WhiskyService
) {
    @PostMapping("/boards/{boardId}/whisky")
    fun likeWhiskyByBoardId(
        @PathVariable boardId: Long
    ): ResponseEntity<CommonResponse<Any>> {
        val createWhiskeyBoard = service.createWhiskeyBoard(WhiskyCreate(id = boardId))
        return if (createWhiskeyBoard != false) {
            ResponseEntity.ok(CommonResponse(createWhiskeyBoard))
        } else {
            ResponseEntity.noContent().build()
        }
    }
}
