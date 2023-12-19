package lyfe.lyfeBe.web.feedback

import jakarta.validation.Valid
import lyfe.lyfeBe.board.service.BoardService
import lyfe.lyfeBe.dto.CommonResponse
import lyfe.lyfeBe.feedback.FeedBackCreate
import lyfe.lyfeBe.feedback.FeedBackService
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/v1/feedbacks")
class FeedbackController (
    private val feedBackService: FeedBackService
){

    @PostMapping
    fun createFeedback(
        @RequestBody @Valid req: CreateFeedbackRequest
    ): CommonResponse<FeedbackIdResponse>
    {
        return CommonResponse(
            FeedbackIdResponse(
                feedBackService.create(
                    FeedBackCreate(
                        userId = req.userId,
                        feedBack = req.feedback
                    )
                )
            )
        )
    }

}