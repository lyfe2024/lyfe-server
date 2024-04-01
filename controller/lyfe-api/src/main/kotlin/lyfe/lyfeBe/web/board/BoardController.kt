package lyfe.lyfeBe.web.board

import jakarta.validation.Valid
import lyfe.lyfeBe.board.*
import lyfe.lyfeBe.board.dto.BestBoardListDto
import lyfe.lyfeBe.board.dto.BoardDto
import lyfe.lyfeBe.board.dto.BoardListDto
import lyfe.lyfeBe.board.dto.SaveBoardDto
import lyfe.lyfeBe.board.service.BoardService
import lyfe.lyfeBe.dto.CommonResponse
import lyfe.lyfeBe.utils.ControllerUtils.Companion.getEffectiveCursorId
import lyfe.lyfeBe.web.board.req.BoardSaveRequest
import lyfe.lyfeBe.web.board.req.BoardUpdateRequest
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort
import org.springframework.data.web.PageableDefault
import org.springframework.web.bind.annotation.*
import java.time.LocalDate

@RestController
@RequestMapping("/v1/boards")
class BoardController(
    private val service: BoardService
) {
    /**
     * 과거 베스트 글 조회
     */
    @GetMapping("/best")
    fun getBestBoards(
        @RequestParam(required = false) cursorId: LocalDate?,
        @PageableDefault(size = 5, page = 0, sort = ["id"], direction = Sort.Direction.DESC) pageable: Pageable,
    ): CommonResponse<BestBoardListDto> {
        return CommonResponse(
            service.getBestBoards(BoardsBestGet(
                cursor = cursorId?: LocalDate.now(),
                pageable = pageable
            ))
        )
    }

    /**
     * 인기글 조회
     */
    @GetMapping("/popular")
    fun getPopularBoardsWithComment(
        @RequestParam(required = false) cursorId: Long,
        @RequestParam popularType: PopularType,
        @PageableDefault(size = 5, page = 0, sort = ["id"], direction = Sort.Direction.DESC) pageable: Pageable,
        @RequestParam(required = false, defaultValue = "BOARD") type: BoardType,
    ): CommonResponse<BoardListDto> {
        val cursorValue = getEffectiveCursorId(cursorId)
        return CommonResponse(service.getPopularBoards(
            BoardsPopularGet(
                cursorId = cursorValue,
                popularType = popularType,
                type = type,
                pageable = pageable
            )))
    }


    /**
     * 최신 글 조회
     */
    @GetMapping("/latest")
    fun getLatestBoards(
        @RequestParam(required = false) cursorId: Long,
        @RequestParam(required = false) date: LocalDate?,
        @PageableDefault(size = 5, page = 0, sort = ["id"], direction = Sort.Direction.DESC) pageable: Pageable,
        @RequestParam(defaultValue = "BOARD") type: BoardType,
    ): CommonResponse<BoardListDto> {
        val cursorValue = getEffectiveCursorId(cursorId)
        return CommonResponse(service.getLatestBoards(
                BoardsGet(
                    cursorId = cursorValue,
                    type = type,
                    date = date,
                    pageable = pageable
                )
            )
        )
    }

    /**
     * 자신이 작성한 글 조회
     */
    @GetMapping("/me")
    fun getMyBoards(
        @RequestParam(required = false) cursorId: Long,
        @RequestParam(defaultValue = "BOARD") type: BoardType,
        @PageableDefault(size = 10, page = 0, sort = ["id"], direction = Sort.Direction.DESC) pageable: Pageable,
        ): CommonResponse<BoardListDto> {
         val cursorValue = getEffectiveCursorId(cursorId)
        return CommonResponse(service.getUserBoards(
                BoardsUserGet(
                    cursorId = cursorValue,
                    type = type,
                    pageable = pageable
                )
            )
        )
    }

    /**
     * 글 상세 조회
     */
    @GetMapping("/detail/{boardId}")
    fun get(
        @PathVariable(value = "boardId") boardId: Long,
    ): CommonResponse<BoardDto>{
        return CommonResponse(service.get(BoardGet(boardId)))
    }

    @PostMapping
    fun create(
        @Valid @RequestBody req: BoardSaveRequest
    ): CommonResponse<SaveBoardDto>{
        return CommonResponse(
            service.create(
                BoardCreate(
                    title = req.title,
                    content = req.content,
                    boardType = req.boardType,
                    topicId = req.topicId,
                    imageUrl = req.imageUrl
                )
            )
        )
    }

    @PutMapping("/{boardId}")
    fun update(
        @PathVariable(value = "boardId") boardId: Long,
        @Valid @RequestBody req: BoardUpdateRequest,
    ) : CommonResponse<SaveBoardDto>{
        return CommonResponse(
            service.update(
                BoardUpdate(
                    boardId = boardId,
                    title = req.title,
                    content = req.content,
                    imageUrl = req.imageUrl,
                )
            )
        )
    }

}
