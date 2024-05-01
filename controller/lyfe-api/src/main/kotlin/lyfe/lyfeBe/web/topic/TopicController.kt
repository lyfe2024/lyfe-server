package lyfe.lyfeBe.web.topic

import jakarta.validation.Valid
import lyfe.lyfeBe.dto.CommonResponse
import lyfe.lyfeBe.topic.TopicCreate
import lyfe.lyfeBe.topic.TopicGet
import lyfe.lyfeBe.topic.TopicPastGet
import lyfe.lyfeBe.topic.TopicUpdate
import lyfe.lyfeBe.topic.dto.SaveTopicDto
import lyfe.lyfeBe.topic.dto.TopicDto
import lyfe.lyfeBe.topic.port.TopicService
import lyfe.lyfeBe.web.topic.req.SaveTopicRequest
import lyfe.lyfeBe.web.topic.req.UpdateTopicRequest
import org.springframework.web.bind.annotation.*
import java.time.LocalDate

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
            TopicGet(topicId = topicId)
        )
    )

    /**
     * 오늘의 주제 조회
     */
    @GetMapping
    fun getTodayTopic(): CommonResponse<TopicDto>{
        return CommonResponse(
            topicService.getToday()
        )
    }

    @GetMapping("/past/{date}")
    fun getPastTopic(
        @PathVariable date: LocalDate
    ): CommonResponse<TopicDto>{
        return CommonResponse(
            topicService.getPast(
                TopicPastGet(date = date)
            )
        )
    }

    @PostMapping
    fun create(
        @Valid @RequestBody req: SaveTopicRequest
    ): CommonResponse<SaveTopicDto>{
        return CommonResponse(
            topicService.create(
                TopicCreate(
                    content = req.content,
                    appliedAt = req.appliedAt
                )
            )
        )
    }

    @PutMapping("/{topicId}")
    fun update(
        @PathVariable topicId: Long,
        @Valid @RequestBody req: UpdateTopicRequest
    ): CommonResponse<SaveTopicDto> {
        return CommonResponse(
            topicService.update(
                TopicUpdate(
                    topicId = topicId,
                    content = req.content,
                    appliedAt = req.appliedAt,
                )
            )
        )
    }
}