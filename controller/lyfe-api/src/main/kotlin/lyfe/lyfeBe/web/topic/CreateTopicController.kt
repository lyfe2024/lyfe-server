package lyfe.lyfeBe.web.topic

import jakarta.validation.Valid
import lyfe.lyfeBe.dto.CommonResponse
import lyfe.lyfeBe.topic.TopicCreate
import lyfe.lyfeBe.topic.port.TopicService
import lyfe.lyfeBe.web.topic.req.SaveTopicRequest
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/v1/topics")
class CreateTopicController(
    val topicService: TopicService
) {

    @PostMapping
    fun createTopic(
        @Valid @RequestBody req: SaveTopicRequest
    ) =
        CommonResponse(
            topicService.create(
                TopicCreate(
                    req.content
                )
            )
        )
}