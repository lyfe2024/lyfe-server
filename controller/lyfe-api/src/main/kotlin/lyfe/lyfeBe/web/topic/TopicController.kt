package lyfe.lyfeBe.web.topic

import jakarta.validation.Valid
import lyfe.lyfeBe.dto.CommonResponse
import lyfe.lyfeBe.topic.*
import lyfe.lyfeBe.topic.port.TopicService
import lyfe.lyfeBe.web.topic.req.SaveTopicRequest
import lyfe.lyfeBe.web.topic.req.UpdateTopicRequest
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort
import org.springframework.data.web.PageableDefault
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/v1/topics")
class TopicController(
    val topicService: TopicService
) {

    @GetMapping("/{topicId}")
    fun get(
        @PathVariable topicId: Long
    ) = CommonResponse(
        topicService.get(
            TopicGet(
                topicId
            )
        )
    )

    @GetMapping
    fun get() = CommonResponse(
        topicService.getToday()
    )

    @GetMapping("/{date}/{cursorId}")
    fun getPastTopic(
        @PathVariable date: String,
        @PathVariable cursorId: Long,
        @PageableDefault(size = 10, page = 0, sort = ["id"], direction = Sort.Direction.DESC) pageable: Pageable,

        ) = CommonResponse(
        topicService.getPast(
            TopicPastGet(
                date, cursorId, pageable
            )
        )
    )


    @PostMapping
    fun create(
        @Valid @RequestBody req: SaveTopicRequest
    ) = CommonResponse(
        topicService.create(
            TopicCreate(
                req.content
            )
        )
    )

    @PutMapping("/{topicId}")
    fun update(
        @PathVariable topicId: Long, @Valid @RequestBody req: UpdateTopicRequest
    ) {
        CommonResponse(
            topicService.update(
                TopicUpdate(
                    topicId, req.content
                )
            )
        )
    }
}