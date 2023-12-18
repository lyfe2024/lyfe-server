package lyfe.lyfeBe.web.whisky

import jakarta.validation.Valid
import lyfe.lyfeBe.dto.CommonResponse
import lyfe.lyfeBe.web.whisky.req.WhiskySaveRequest
import lyfe.lyfeBe.whisky.WhiskyCreate
import lyfe.lyfeBe.whisky.WhiskyDelete
import lyfe.lyfeBe.whisky.WhiskyService
import lyfe.lyfeBe.whisky.dto.SaveWhiskyDto
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/v1/whiskies")
class WhiskyController(
    private val service: WhiskyService
) {

    @PostMapping("/boards/likes")
    fun create(
        @RequestBody @Valid req: WhiskySaveRequest
    ): CommonResponse<SaveWhiskyDto> =
        CommonResponse(
            service.create(
                WhiskyCreate(
                    req.boardId, req.userId
                )
            )
        )


    @DeleteMapping("/boards/{boardId}/users/{userId}/like")
    fun delete(
        @PathVariable boardId: Long,
        @PathVariable userId: Long

    ) = service.delete(
        WhiskyDelete(
            boardId, userId
        )
    )
}
