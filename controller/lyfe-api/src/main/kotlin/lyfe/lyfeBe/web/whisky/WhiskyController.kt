package lyfe.lyfeBe.web.whisky

import jakarta.validation.Valid
import lyfe.lyfeBe.web.whisky.req.WhiskySaveRequest
import lyfe.lyfeBe.whisky.WhiskyCreate
import lyfe.lyfeBe.whisky.WhiskyService
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/v1/whiskies")
class WhiskyController(
    private val service: WhiskyService
) {
    @PostMapping("/boards")
    fun create(
        @RequestBody @Valid req: WhiskySaveRequest
    ) {
        service.switchWhiskyLikeState(
            WhiskyCreate(
                req.boardId, req.userId
            )
        )
    }
}
