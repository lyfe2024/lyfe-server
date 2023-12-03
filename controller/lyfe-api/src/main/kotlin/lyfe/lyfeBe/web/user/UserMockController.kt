package lyfe.lyfeBe.web.user

import lyfe.lyfeBe.board.BoardType
import lyfe.lyfeBe.dto.CommonResponse
import lyfe.lyfeBe.dto.PageInfo
import lyfe.lyfeBe.web.board.*
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/v1/users")
class UserMockController {

    @GetMapping("/me")
    fun getMe(): CommonResponse<UserResponse> {
        return CommonResponse(
            UserResponse(
                id = 1L,
                username = "홍길동",
                profile = "https://picsum.photos/700/700"
            )
        )
    }

    @PatchMapping("/me")
    fun updateMe(
        @RequestBody updateRequest: UpdateUserRequest
    ): CommonResponse<UserResponse> {
        return CommonResponse(
            UserResponse(
                id = 1L,
                username = "홍길동2",
                profile = "https://picsum.photos/700/700"
            )
        )
    }

    @GetMapping("/me/boards/picture")
    fun getMyBoardPictureList(): CommonResponse<BoardPictureListResponse>{
        val boardList = generateBoardList(10) { i ->
            BoardPictureResponse(
                id = i.toLong(),
                title = "타이틀$i",
                picture = PictureResponse(
                    height = 420,
                    width = 420,
                    pictureUrl = "https://picsum.photos/420/420"
                ),
                date = "2021-01-01",
                boardType = BoardType.BOARD_PICTURE,
                user = UserResponse(
                    id = 1L,
                    username = "홍길동",
                    profile = "https://picsum.photos/700/700"
                ),
                whiskyCount = 1,
                commentCount = 1
            )
        }

        return CommonResponse(
            result = BoardPictureListResponse(boardList),
            page = PageInfo(
                size = 10,
                number = 1,
                totalElements = 10,
                totalPages = 1
            )
        )
    }

    @GetMapping("/me/boards/content")
    fun getMyBoardList(): CommonResponse<BoardContentListResponse>{
        val boardCONTENTList = generateBoardList(10) { i ->
            BoardContentResponse(
                id = i.toLong(),
                title = "타이틀$i",
                content = "내용$i",
                boardType = BoardType.BOARD_CONTENT,
                createdAt = "2021-01-01",
                updatedAt = "2021-01-01",
                user = UserResponse(
                    id = 1L,
                    username = "홍길동",
                    profile = "https://picsum.photos/700/700"
                ),
                whiskyCount = 1,
                commentCount = 1,
                isLike = true
            )
        }

        return CommonResponse(
            result = BoardContentListResponse(boardCONTENTList),
            page = PageInfo(
                size = 10,
                number = 1,
                totalElements = 10,
                totalPages = 1
            )
        )
    }

    @GetMapping("/check-nickname/{nickname}")
    fun checkNickname(
        @PathVariable nickname: String
    ): CommonResponse<CheckNicknameResponse> {
        val isAvailable = !nickname.endsWith("2")
        check(isAvailable)
        return CommonResponse(CheckNicknameResponse(isAvailable = isAvailable))
    }


    private inline fun <reified T> generateBoardList(size: Int, createBoardFn: (Int) -> T): List<T> {
        return (1..size).map { createBoardFn(it) }
    }



}