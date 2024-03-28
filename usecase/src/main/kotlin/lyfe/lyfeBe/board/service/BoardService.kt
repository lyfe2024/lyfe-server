package lyfe.lyfeBe.board.service

import lyfe.lyfeBe.Constants.Companion.CURSOR_VALUE
import lyfe.lyfeBe.board.*
import lyfe.lyfeBe.board.dto.BoardDto
import lyfe.lyfeBe.board.dto.BoardDtoAssembly
import lyfe.lyfeBe.board.dto.SaveBoardDto
import lyfe.lyfeBe.board.dto.UpdateBoardDto
import lyfe.lyfeBe.board.port.out.BoardPort
import lyfe.lyfeBe.comment.port.out.CommentPort
import lyfe.lyfeBe.topic.port.TopicPort
import lyfe.lyfeBe.user.port.out.UserPort
import lyfe.lyfeBe.whisky.out.WhiskyPort
import org.springframework.stereotype.Service

@Service
class BoardService(
    private val boardPort: BoardPort,
    private val userPort: UserPort,
    private val topicPort: TopicPort,
    private val whiskyPort: WhiskyPort,
    private val commentPort: CommentPort

) {



    fun getById(id: Long): Board {
        return boardPort.getById(id)
    }

    fun get(boardGet: BoardGet): BoardDto {

        val board = getById(boardGet.id)

        val whiskyCount = fetchWhiskyCount(board.id)
        val commentCount = fetchCommentCount(board.id)

        val params = BoardDtoAssembly(board, whiskyCount, commentCount)

        return BoardDto.toBoardDto(params)
    }



    fun getBoards(boardsGet: BoardsGet): List<BoardDto> {

        val boards = boardPort.findByIdCursorId(boardsGet.boardId, boardsGet.date, boardsGet.pageable, boardsGet.type).toList()

        return boards.map { board ->
            val whiskyCount = fetchWhiskyCount(board.id)
            val commentCount = fetchCommentCount(board.id)
            val params = BoardDtoAssembly(board, whiskyCount, commentCount)
            BoardDto.toBoardDto(params)
        }.toList()
    }


    fun getPopularBoards(boardsPopularGet: BoardsPopularGet): List<BoardDto> {

        val boards = boardPort.findPopularBoards(
            boardsPopularGet.whiskyCount,
            boardsPopularGet.count,
            boardsPopularGet.date,
            boardsPopularGet.type
        )

        val boardWithWhiskyCounts = boards.map { board ->
            val whiskyCount = fetchWhiskyCount(board.id)
            Pair(board, whiskyCount)
        }

        val sortedBoards = boardWithWhiskyCounts.sortedByDescending { it.second }

        return sortedBoards.map { (board, whiskyCount) ->
            val commentCount = fetchCommentCount(board.id)
            val params = BoardDtoAssembly(board, whiskyCount, commentCount)

            BoardDto.toBoardDto(params)
        }
    }

    fun getUserBoards(boardUserGet: BoardsUserGet): List<BoardDto> {

        val boards = boardPort.findByUserAndBoardType(
            boardUserGet.userId,
            boardUserGet.cursorId,
            boardUserGet.type,
            boardUserGet.pageable
        )

        return boards.map { board ->
            val whiskyCount = fetchWhiskyCount(board.id)
            val commentCount = fetchCommentCount(board.id)
            val params = BoardDtoAssembly(board, whiskyCount, commentCount)
            BoardDto.toBoardDto(params)
        }.toList()
    }


    fun create(boardCreate: BoardCreate): SaveBoardDto {
        val user = userPort.getById(boardCreate.userId)
        val topic = topicPort.getById(boardCreate.topicId)
        val board = Board.from(boardCreate, user, topic)
        return SaveBoardDto(boardPort.create(board).id)
    }


    fun update(boardUpdate: BoardUpdate): UpdateBoardDto {
        val board = getById(boardUpdate.boardId).update(boardUpdate,boardUpdate.userId)
        return UpdateBoardDto(boardPort.update(board).id)
    }

    private fun fetchCommentCount(boardId: Long) = commentPort.countByBoardId(boardId)

    private fun fetchWhiskyCount(boardId: Long) = whiskyPort.countByBoardId(boardId)

}