package lyfe.lyfeBe.web.comment

import lyfe.lyfeBe.comment.port.`in`.GetCommentByBoardCommand
import lyfe.lyfeBe.comment.port.`in`.GetCommentByUserIdCommand
import lyfe.lyfeBe.comment.service.GetCommentService
import lyfe.lyfeBe.dto.CommonResponse
import lyfe.lyfeBe.dto.PageInfo
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.data.web.PageableDefault
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
class GetCommentController(
    private val service: GetCommentService
) {


    /**
     * 댓글 id 로 1건 조회
     */
    @GetMapping("/v1/comments/{commentId}")
    fun getComment(
        @PathVariable commentId: String
    ): CommonResponse<CommentResponse> {
        return service.getComment(commentId.toLong())
            .let { CommentMapper.mapToResponse(it) }
            .let { CommonResponse(it) }
    }

    /**
     * 댓글 최근 목록 조회
     */
    @GetMapping("/v1/comments/latest")
    fun getCommentList(
        @RequestParam(name = "comment_board_id") boardId: Long,
        @RequestParam(required = false) cursorId: Long,
        @PageableDefault(size = 10, sort = ["id"], direction = Sort.Direction.DESC) pageable: PageRequest
    ): CommonResponse<CommentListResponse> {

        val commentList = service.findAllByBoard(
            GetCommentByBoardCommand(
                boardId = boardId,
                cursorId = cursorId,
                pageable = pageable

            )
        )
        val response = CommentListResponse(
            commentList = commentList.content.map { CommentMapper.mapToResponse(it) }
        )

        return CommonResponse(
            result = response,
            page = PageInfo.of(page = commentList)
        )
    }

    /**
     * 자신의 댓글 목록 조회
     */
    @GetMapping("/v1/comments")
    fun getMyCommentList(
        @RequestParam(name = "comment_user_id") userId: Long,
        @RequestParam(name = "comment_board_id") boardId: Long,
        @RequestParam(required = false) cursorId: Long,
        @PageableDefault(size = 10, sort = ["id"], direction = Sort.Direction.DESC) pageable: PageRequest
    ): CommonResponse<CommentListResponse> {

        val commentList = service.findAllByUserId(
            GetCommentByUserIdCommand(
                boardId = boardId,
                userId = userId,
                cursorId = cursorId,
                pageable = pageable
            )
        )
        val response = CommentListResponse(
            commentList = commentList.content.map { CommentMapper.mapToResponse(it) }
        )

        return CommonResponse(
            result = response,
            page = PageInfo.of(page = commentList)
        )
    }


}