package lyfe.lyfeBe.web.board

import jakarta.validation.Valid
import lyfe.lyfeBe.board.*
import lyfe.lyfeBe.board.dto.BoardDto
import lyfe.lyfeBe.board.service.BoardService
import lyfe.lyfeBe.dto.CommonResponse
import lyfe.lyfeBe.utils.ControllerUtils.Companion.getEffectiveCursorId
import lyfe.lyfeBe.web.board.req.BoardSaveRequest
import lyfe.lyfeBe.web.board.req.BoardUpdateRequest
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort
import org.springframework.data.web.PageableDefault
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/v1/boards")
class BoardController(
    private val service: BoardService
) {

    //보드 최신순
    @GetMapping("/{cursorId}")
    fun getBoards(
        @PathVariable(required = false) cursorId: Long?,
        @RequestParam(required = false) date: String?,
        @PageableDefault(size = 5, page = 0, sort = ["id"], direction = Sort.Direction.DESC) pageable: Pageable,
        @RequestParam(required = false, defaultValue = "BOARD") type: BoardType,
    ): CommonResponse<List<BoardDto>> {
        val boardId = getEffectiveCursorId(cursorId)
        return CommonResponse(service.getBoards(BoardsGet(boardId, date, pageable , type)))
    }

    // 사진없는 보드 인기글  + 오늘의 베스트 count 요청 갯수  OPTION , date Option
    @GetMapping("/popular/{whiskyCount}")
    fun getPopularBoards(
        @PathVariable whiskyCount: Long,
        @RequestParam(required = false) date: String?,
        @RequestParam(required = false, defaultValue = "5") count: Int,
        @RequestParam(required = false, defaultValue = "BOARD") type: BoardType
    ): CommonResponse<List<BoardDto>> {
        return CommonResponse(service.getPopularBoards(BoardsPopularGet(date, whiskyCount, type, count)))
    }
    // 사용자 보드 리스트
    @GetMapping("/{userId}/{type}")
    fun getUserBoards(
        @PathVariable userId: Long,
        @RequestParam(required = false) cursorId: Long?,
        @RequestParam(required = false, defaultValue = "BOARD") type: BoardType,
        @PageableDefault(size = 5, page = 0, sort = ["id"], direction = Sort.Direction.DESC) pageable: Pageable,
        ): CommonResponse<List<BoardDto>> {
         val cursorValue = getEffectiveCursorId(cursorId)
        return CommonResponse(service.getUserBoards(BoardsUserGet(userId, cursorValue, type, pageable)))
    }

    // 사진없는글 단건 조회
    @GetMapping("/detail/{boardId}")
    fun get(
        @PathVariable(value = "boardId") boardId: Long,
    ) = CommonResponse(service.get(BoardGet(boardId)))

    @PostMapping
    fun create(
        @Valid @RequestBody req: BoardSaveRequest
    ) = CommonResponse(
        service.create(
            BoardCreate(
                title = req.title,
                content = req.content,
                boardType = req.boardType,
                userId = req.userId,
                topicId = req.topicId,
                imageUrl = req.imageUrl
            )
        )
    )


    @PutMapping("/{boardId}")
    fun update(
        @PathVariable(value = "boardId") boardId: Long,
        @Valid @RequestBody req: BoardUpdateRequest
    ) = CommonResponse(
        service.update(
            BoardUpdate(
                boardId = boardId,
                title = req.title,
                content = req.content,
                imageUrl = req.imageUrl
            )
        )
    )
}
