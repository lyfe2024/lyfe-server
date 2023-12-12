package lyfe.lyfeBe.web.topic

import lyfe.lyfeBe.dto.CommonResponse
import lyfe.lyfeBe.topic.TopicGet
import lyfe.lyfeBe.topic.TopicPastGet
import lyfe.lyfeBe.topic.port.TopicService
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort
import org.springframework.data.web.PageableDefault
import org.springframework.web.bind.annotation.*

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

    @GetMapping("/{date}/{cursorId}")
    fun getPastTopic(
        @PathVariable date: String,
        @PathVariable cursorId: Long,
        @PageableDefault(size = 10, page = 0, sort = ["id"], direction = Sort.Direction.DESC) pageable: Pageable,

    ) =
        CommonResponse(
            topicService.getPast(
                TopicPastGet(
                    date,
                    cursorId,
                    pageable
                )
            )
        )

}