package lyfe.lyfeBe.board.service

import lyfe.lyfeBe.auth.service.SecurityUtils
import lyfe.lyfeBe.auth.service.SecurityUtils.getLoginUser
import lyfe.lyfeBe.board.*
import lyfe.lyfeBe.board.dto.BoardDto
import lyfe.lyfeBe.board.dto.BoardDtoAssembly
import lyfe.lyfeBe.board.dto.BoardListDto
import lyfe.lyfeBe.board.dto.SaveBoardDto
import lyfe.lyfeBe.board.port.out.BoardPort
import lyfe.lyfeBe.comment.port.out.CommentPort
import lyfe.lyfeBe.error.ForbiddenException
import lyfe.lyfeBe.topic.port.TopicPort
import lyfe.lyfeBe.user.port.out.UserPort
import lyfe.lyfeBe.whisky.out.WhiskyPort
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDate

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

//    /**
//     * 과거 베스트 글 목록 조회
//     */
//    fun getBestBoards(boardsBestGet: BoardsBestGet): BestBoardListDto {
//        val boardPictures = boardPort.getBoardWithCursorAndTopic(
//            cursorId = 0,
//            type = BoardType.BOARD_PICTURE,
//            topicId = topicPort.getDate(LocalDate.now()).id,
//            pageable = Pageable.ofSize(3)
//        )
//
//        val boards = boardPort.getBoardWithCursorAndTopic(
//            cursorId = 0,
//            type = BoardType.BOARD,
//            topicId = topicPort.getDate(LocalDate.now()).id,
//            pageable = Pageable.ofSize(3)
//        )
//
//
//    }


    /**
     * 게시글 날짜별 최신 목록 조회
     */
    fun getLatestBoards(boardsGet: BoardsGet): BoardListDto {

        val topic: Long = if (boardsGet.date != null) {
            topicPort.getDate(boardsGet.date!!).id
        }else {
            topicPort.getDate(LocalDate.now()).id
        }
        val boards =
            boardPort.getBoardWithCursorAndTopic(boardsGet.cursorId, boardsGet.type, topic, boardsGet.pageable).toList()

        return BoardListDto.toListDto(
            boards.map { board ->
                val whiskyCount = fetchWhiskyCount(board.id)
                val commentCount = fetchCommentCount(board.id)
                val params = BoardDtoAssembly(board, whiskyCount, commentCount)
                BoardDto.toBoardDto(params)
            }.toList()
        )
    }

    /**
     * 인기글 조회
     */
    fun getPopularBoards(boardsPopularGet: BoardsPopularGet): BoardListDto {

        val topicId = topicPort.getDate(LocalDate.now()).id

        val popularType = boardsPopularGet.popularType
        val boards: List<Board>
        if (popularType == PopularType.WHISKY) {
            boards = boardPort.findPopularBoardsWithWhisky(
                cursorId = boardsPopularGet.cursorId,
                topicId = topicId,
                type = boardsPopularGet.type,
                pageable = boardsPopularGet.pageable
            )
        }else if (popularType == PopularType.COMMENT) {
            boards = boardPort.findPopularBoardsWithComment(
                cursorId = boardsPopularGet.cursorId,
                topicId = topicId,
                type = boardsPopularGet.type,
                pageable = boardsPopularGet.pageable
            )
        }else{
            throw IllegalArgumentException("Invalid popularType")
        }

        return BoardListDto.toListDto(
            boards.map { board ->
                val whiskyCount = fetchWhiskyCount(board.id)
                val commentCount = fetchCommentCount(board.id)
                val params = BoardDtoAssembly(board, whiskyCount, commentCount)
                BoardDto.toBoardDto(params)
            }.toList()
        )
    }

    /**
     * 자신이 작성한 글 조회
     */
    fun getUserBoards(boardUserGet: BoardsUserGet): BoardListDto {

        val user = getLoginUser(userPort)
        val boards = boardPort.getBoardsWithCursorAndUser(
            cursorId = boardUserGet.cursorId,
            type = boardUserGet.type,
            userId = user.id,
            pageable = boardUserGet.pageable
        )

        return BoardListDto.toListDto(
            boards.map { board ->
                val whiskyCount = fetchWhiskyCount(board.id)
                val commentCount = fetchCommentCount(board.id)
                val params = BoardDtoAssembly(board, whiskyCount, commentCount)
                BoardDto.toBoardDto(params)
            }.toList()
        )
    }

    @Transactional
    fun create(boardCreate: BoardCreate): SaveBoardDto {
        val user = getLoginUser(userPort)
        val topic = topicPort.getById(boardCreate.topicId)
        val board = Board.from(boardCreate, user, topic)
        return SaveBoardDto(boardPort.create(board).id)
    }

    @Transactional
    fun update(boardUpdate: BoardUpdate): SaveBoardDto {
        val board = getById(boardUpdate.boardId).update(boardUpdate)

        if (board.user.id != SecurityUtils.getLoginUserId(userPort)) {
            throw ForbiddenException("자신의 글만 수정할 수 있습니다.")
        }

        return SaveBoardDto(boardPort.update(board).id)
    }

    private fun fetchCommentCount(boardId: Long) = commentPort.countByBoardId(boardId)

    private fun fetchWhiskyCount(boardId: Long) = whiskyPort.countByBoardId(boardId)

}