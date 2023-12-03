package lyfe.lyfeBe.web.comment

import lyfe.lyfeBe.dto.CommonResponse
import lyfe.lyfeBe.dto.PageInfo
import lyfe.lyfeBe.web.user.UserResponse
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class CommentMockController {


    @PostMapping("/v1/boards/{boardId}/comments")
    fun createComment(
        @PathVariable boardId: String,
        @RequestBody createCommentRequest: CreateCommentRequest
    ): CommonResponse<CommentIdResponse> {
        return CommonResponse(CommentIdResponse(1L))
    }

    @GetMapping("/v1/boards/{boardId}/comments")
    fun getCommentList(
        @PathVariable boardId: String
    ): CommonResponse<CommentListResponse> {

        val commentList = mutableListOf<CommentResponse>()
        for (i in 1..10) {
            val commentResponse = CommentResponse(
                id = i.toLong(),
                content = "댓글$i",
                commentGroupId = null,
                user = UserResponse(
                    id = 1L,
                    username = "홍길동",
                    profile = ""
                ),
                createdAt = "2021-01-01"
            )
            commentList.add(commentResponse)
        }

        return CommonResponse(
            result = CommentListResponse(commentList),
            page = PageInfo(
                size = 10,
                number = 1,
                totalElements = 10,
                totalPages = 1
            )
        )
    }
}