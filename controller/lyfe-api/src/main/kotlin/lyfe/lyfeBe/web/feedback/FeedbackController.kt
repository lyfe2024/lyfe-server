package lyfe.lyfeBe.web.feedback

import lyfe.lyfeBe.dto.CommonResponse
import lyfe.lyfeBe.feedback.FeedbackCreate
import lyfe.lyfeBe.feedback.dto.SaveFeedbackDto
import lyfe.lyfeBe.feedback.service.FeedbackService
import lyfe.lyfeBe.web.feedback.req.SaveFeedbackRequest
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/v1/feedbacks")
class FeedbackController(
    private val service: FeedbackService
) {

    @PostMapping("")
    fun createFeedback(
        @RequestBody saveFeedbackRequest: SaveFeedbackRequest
    ): CommonResponse<SaveFeedbackDto> {
        return service.create(
            FeedbackCreate(
                feedback = saveFeedbackRequest.feedback
            )
        ).let { CommonResponse(it) }
    }

}