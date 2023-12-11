package lyfe.lyfeBe.web.topic

import lyfe.lyfeBe.dto.CommonResponse
import lyfe.lyfeBe.topic.TopicGet
import lyfe.lyfeBe.topic.port.TopicService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/v1/topics")
class GetTopicController(
    val topicService: TopicService
) {

    @GetMapping("/{topicId}")
    fun get(
        @PathVariable topicId: Long
    ) =
        CommonResponse(
            topicService.get(
                TopicGet(
                    topicId
                )
            )
        )

}