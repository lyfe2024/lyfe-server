package lyfe.lyfeBe.board.service

import lyfe.lyfeBe.board.*
import lyfe.lyfeBe.board.dto.BoardDto
import lyfe.lyfeBe.board.dto.BoardDtoAssembly
import lyfe.lyfeBe.board.dto.SaveBoardDto
import lyfe.lyfeBe.board.dto.UpdateBoardDto
import lyfe.lyfeBe.board.port.out.BoardPort
import lyfe.lyfeBe.comment.port.out.CommentPort
import lyfe.lyfeBe.image.port.out.ImagePort
import lyfe.lyfeBe.topic.port.TopicPort
import lyfe.lyfeBe.user.port.out.UserPort
import lyfe.lyfeBe.whisky.out.WhiskyPort
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service

@Service
class BoardService(
    private val boardport: BoardPort,
    private val userport: UserPort,
    private val topicport: TopicPort,
    private val imageport: ImagePort,
    private val whiskyPort: WhiskyPort,
    private val commentport: CommentPort
) {


    fun get(boardGet: BoardGet): BoardDto {

        val board = getById(boardGet.id)
        val image = imageport.getByUserId(board.user.id)
        val whiskyCount = fetchWhiskyCount(board.id)
        val commentCount = fetchCommentCount(board.id)

        val params = BoardDtoAssembly(board, image.url, whiskyCount, commentCount)

        return BoardDto.toDto(params)
    }


    fun getBoards(boardsGet: BoardsGet): List<BoardDto> {
        val boards = fetchBoards(boardsGet.boardId, boardsGet.pageable)

        return boards.map { board ->
            val image = fetchImageUrl(board.user.id)
            val whiskyCount = fetchWhiskyCount(board.id)
            val commentCount = fetchCommentCount(board.id)
            val params = BoardDtoAssembly(board, image, whiskyCount, commentCount)

            BoardDto.toDto(params)
        }.toList()
    }


    fun getPopularBoards(boardsGet: BoardsGet): List<BoardDto> {
        val boards = fetchBoards(boardsGet.boardId, boardsGet.pageable)

        val boardWithWhiskyCounts = boards.map { board ->
            val whiskyCount = fetchWhiskyCount(board.id)
            Pair(board, whiskyCount)
        }

        val sortedBoards = boardWithWhiskyCounts.sortedByDescending { it.second }

        return sortedBoards.map { (board, whiskyCount) ->
            val image = fetchImageUrl(board.user.id)
            val commentCount = fetchCommentCount(board.id)
            val params = BoardDtoAssembly(board, image, whiskyCount, commentCount)

            BoardDto.toDto(params)
        }
    }

    fun create(boardCreate: BoardCreate): SaveBoardDto {
        val user = userport.getById(boardCreate.userId)
        val topic = topicport.getById(boardCreate.topicId)
        val board = Board.from(boardCreate, user, topic)
        return SaveBoardDto(boardport.create(board).id)
    }


    fun update(boardUpdate: BoardUpdate): UpdateBoardDto {
        val board = getById(boardUpdate.boardId).update(boardUpdate)
        return UpdateBoardDto(boardport.update(board).id)
    }

    fun getById(id: Long): Board {
        return boardport.getById(id)
    }


    private fun fetchBoards(boardId: Long, pageable: Pageable): List<Board> {
        return boardport.findByIdCursorId(boardId, pageable).toList()
    }

    private fun fetchImageUrl(userId: Long): String {
        return imageport.getByUserId(userId).url
    }

    private fun fetchCommentCount(boardId: Long) = commentport.countByBoardId(boardId)

    private fun fetchWhiskyCount(boardId: Long) = whiskyPort.countByBoardId(boardId)

}