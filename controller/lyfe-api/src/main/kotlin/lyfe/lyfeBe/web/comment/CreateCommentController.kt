package lyfe.lyfeBe.web.comment

import jakarta.validation.Valid
import lyfe.lyfeBe.comment.dto.CommentDto
import lyfe.lyfeBe.comment.CreateComment
import lyfe.lyfeBe.comment.service.CreateCommentService
import lyfe.lyfeBe.dto.CommonResponse
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
class CreateCommentController(
    private val service: CreateCommentService
) {
    @PostMapping("/v1/comments")
    fun createComment(
        @Valid @RequestBody req: CreateCommentRequest,
        @RequestParam("comment_board_id") boardId: Long,

        ): CommonResponse<CommentDto> {

        return service.create(
            CreateComment(
                content = req.content,
                commentGroupId = req.commentGroupId,
                userId = 1L,
                boardId = boardId
            )
        ).let { CommonResponse(it) }

    }
}