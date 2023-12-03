package lyfe.lyfeBe.web.topic

import lyfe.lyfeBe.dto.CommonResponse
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/v1/topics")
class TopicMockController {

    @GetMapping("")
    fun getTopicList(): CommonResponse<TopicResponse> {
        val topicList = TopicResponse(
            topic = "topic",
            date = "2023.01.01"
        )
        return CommonResponse(topicList)
    }

    @GetMapping("/{date}")
    fun getTopicDetail(
        @PathVariable date: String
    ): CommonResponse<TopicResponse> {
        val topicDetail = TopicResponse(
            topic = "topic_past",
            date = "2023.01.01"
        )
        return CommonResponse(topicDetail)
    }
}