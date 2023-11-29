package lyfe.lyfeBe.web.comment

import lyfe.lyfeBe.comment.port.`in`.CreateCommentUseCase
import lyfe.lyfeBe.dto.CommonResponse
import org.springframework.security.core.Authentication
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class CreateCommentController(
    private val createCommentUseCase: CreateCommentUseCase
) {
    @PostMapping("/v1/boards/{boardId}/comments")
    fun createComment(
        @RequestBody request: CreateCommentRequest,
        @PathVariable boardId: Long,
        authentication: Authentication,

        ): CommonResponse<CommentResponse> {

        return createCommentUseCase.create(
            content = request.content,
            commentGroupId = request.commentGroupId,
            userId = authentication.name.toLong(),
            boardId = boardId
        )
            .let { CommentMapper.mapToResponse(it) }
            .let { CommonResponse(it) }


    }
}