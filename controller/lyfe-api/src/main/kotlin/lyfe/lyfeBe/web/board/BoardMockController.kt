package lyfe.lyfeBe.web.board

import lyfe.lyfeBe.board.BoardType
import lyfe.lyfeBe.dto.CommonResponse
import lyfe.lyfeBe.dto.PageInfo
import lyfe.lyfeBe.web.user.UserResponse
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/v1/boards")
class BoardMockController {

    @GetMapping("/picture/latest")
    fun getLatestPictureBoardList(): CommonResponse<BoardPictureListResponse> {
        val boardList = generateBoardList(5) { i ->
            BoardPictureResponse(
                id = i.toLong(),
                title = "타이틀$i",
                picture = PictureResponse(
                    height = 420,
                    width = 420,
                    pictureUrl = "https://picsum.photos/420/420"
                ),
                date = "2021-01-01",
                boardType = BoardType.BOARD_CONTENT,
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

    @GetMapping("/picture/hot")
    fun getHotPictureBoardList(): CommonResponse<BoardPictureListResponse> {
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

    @GetMapping("/content/latest")
    fun getLatestContentBoardList(): CommonResponse<BoardContentListResponse> {
        val boardCONTENTList = generateBoardList(10) { i ->
            BoardContentResponse(
                id = i.toLong(),
                title = "타이틀$i",
                content = "컨텐츠$i",
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

    @GetMapping("/content/hot")
    fun getHotContentBoardList(): CommonResponse<BoardContentListResponse> {
        val boardCONTENTList = generateBoardList(10) { i ->
            BoardContentResponse(
                id = i.toLong(),
                title = "타이틀$i",
                content = "컨텐츠$i",
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

    @GetMapping("/{boardId}")
    fun getBoardDetail(
        @PathVariable boardId: String
    ): CommonResponse<Any> {

        val isEven = boardId.toInt() % 2 == 0

        val boardCONTENTDetailResponse: Any = if (isEven) {
            BoardDetailResponse(
                id = 1L,
                title = "타이틀",
                content = "컨텐츠",
                boardType = BoardType.BOARD_CONTENT,
                picture = null,
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
        } else {
            BoardDetailResponse(
                id = 1L,
                title = "타이틀",
                content = null,
                picture = PictureResponse(
                    height = 420,
                    width = 420,
                    pictureUrl = "https://picsum.photos/420/420"
                ),
                boardType = BoardType.BOARD_PICTURE,
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

        return CommonResponse(boardCONTENTDetailResponse)
    }

    @PostMapping()
    fun createBoard(
        @RequestBody createBoardRequest: CreateBoardRequest
    ): CommonResponse<BoardIdResponse> {
        return CommonResponse(BoardIdResponse(1L))
    }

    @PatchMapping("/{boardId}")
    fun updateBoard(
        @PathVariable boardId: String,
        @RequestBody updateBoardRequest: UpdateBoardRequest
    ): CommonResponse<BoardIdResponse> {
        return CommonResponse(BoardIdResponse(1L))
    }

    @GetMapping("/best/{date}")
    fun getBestBoardList(
        @PathVariable date: String
    ): CommonResponse<BoardBestListResponse> {

        val boardPictureList = generateBoardList(3) { i ->
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

        val boardCONTENTList = generateBoardList(3) { i ->
            BoardContentResponse(
                id = i.toLong(),
                title = "타이틀$i",
                content = "컨텐츠$i",
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
            BoardBestListResponse(
                boardPictureList = boardPictureList,
                boardContentList = boardCONTENTList,
                topic = "토픽 내용",
                date = date
            )
        )
    }

    @PatchMapping("/{boardId}/like")
    fun likePictureBoard(
        @PathVariable boardId: String
    ): CommonResponse<BoardIdResponse> {
        return CommonResponse(BoardIdResponse(1L))
    }

    private inline fun <reified T> generateBoardList(size: Int, createBoardFn: (Int) -> T): List<T> {
        return (1..size).map { createBoardFn(it) }
    }
}
