package lyfe.lyfeBe.web.feedback

import lyfe.lyfeBe.dto.CommonResponse
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/v1/feedbacks")
class FeedbackMockController {

    @PostMapping("")
    fun createFeedback(
        @RequestBody createFeedbackRequest: CreateFeedbackRequest
    ): CommonResponse<FeedbackIdResponse> {
        return CommonResponse(FeedbackIdResponse(1L))
    }

}