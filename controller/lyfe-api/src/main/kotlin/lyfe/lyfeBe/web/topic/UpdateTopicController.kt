package lyfe.lyfeBe.web.topic

import jakarta.validation.Valid
import lyfe.lyfeBe.dto.CommonResponse
import lyfe.lyfeBe.topic.TopicUpdate
import lyfe.lyfeBe.topic.port.TopicService
import lyfe.lyfeBe.web.topic.req.UpdateTopicRequest
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/v1/topics")
class UpdateTopicController(
    val topicService: TopicService
) {

    @PutMapping("/{topicId}")
    fun updateTopic(
        @Valid @RequestBody req: UpdateTopicRequest,
        @PathVariable topicId: Long
    ) {
        CommonResponse(
            topicService.update(
                TopicUpdate(
                    topicId,
                    req.content
                )
            )
        )
    }
}