package lyfe.lyfeBe.web.comment

import lyfe.lyfeBe.comment.port.`in`.CreateCommentCommand
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
        @RequestBody request: CreateCommentRequest,
        @RequestParam("comment_board_id") boardId: Long,

        ): CommonResponse<CommentResponse> {

        return service.create(
            CreateCommentCommand(
                content = request.content,
                commentGroupId = request.commentGroupId,
                userId = 1L,
                boardId = boardId
            )
        )
            .let { CommentMapper.mapToResponse(it) }
            .let { CommonResponse(it) }


    }
}