package lyfe.lyfeBe.web.board

import lyfe.lyfeBe.board.BoardGet
import lyfe.lyfeBe.board.BoardService
import lyfe.lyfeBe.board.BoardsGet
import lyfe.lyfeBe.dto.CommonResponse
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort
import org.springframework.data.web.PageableDefault
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("boards")
class BoardGetController(
        private val service: BoardService
) {
    @GetMapping
    fun getBoards(
            @PageableDefault(size = 5, page = 0, sort = ["id"], direction = Sort.Direction.DESC) pageable: Pageable
    ) = CommonResponse(service.getBoards(BoardsGet(pageable)))


    @GetMapping("{boardId}")
    fun get(
            @PathVariable(value = "boardId") boardId: Long,
    ) = CommonResponse(service.get(BoardGet(boardId)))
}
