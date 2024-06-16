package lyfe.lyfeBe.board.service

import lyfe.lyfeBe.auth.service.SecurityUtils.getLoginUser
import lyfe.lyfeBe.board.*
import lyfe.lyfeBe.board.dto.*
import lyfe.lyfeBe.board.port.out.BoardPort
import lyfe.lyfeBe.comment.port.out.CommentPort
import lyfe.lyfeBe.error.ForbiddenException
import lyfe.lyfeBe.topic.port.TopicPort
import lyfe.lyfeBe.user.User
import lyfe.lyfeBe.user.UserStatus
import lyfe.lyfeBe.user.port.out.UserPort
import lyfe.lyfeBe.whisky.out.WhiskyPort
import org.springframework.data.domain.PageRequest
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

    /**
     * 과거 베스트 글 목록 조회
     */
    fun getBestBoards(boardsBestGet: BoardsBestGet): BestBoardListDto {
        val dates = boardPort.findUniqueDatesBeforeCursor(boardsBestGet.cursor, boardsBestGet.pageable)

        val bestBoardsList = dates.map { date ->
            val boards = boardPort.findByDateAndType(date, BoardType.BOARD, PageRequest.of(0, 3))
            val boardPictures = boardPort.findByDateAndType(date, BoardType.BOARD_PICTURE, PageRequest.of(0, 3))

            BestBoardDto(
                date = date,
                topic = topicPort.getDate(date).content,
                boardList = boards.map { board ->
                    val whiskyCount = fetchWhiskyCount(board.id)
                    val commentCount = fetchCommentCount(board.id)
                    val params = BoardDtoAssembly(board, whiskyCount, commentCount)
                    BoardDto.toBoardDto(params)
                }.toList(),
                boardPictureList = boardPictures.map { board ->
                    val whiskyCount = fetchWhiskyCount(board.id)
                    val commentCount = fetchCommentCount(board.id)
                    val params = BoardDtoAssembly(board, whiskyCount, commentCount)
                    BoardDto.toBoardDto(params)
                }.toList()
            )
        }

        return BestBoardListDto(list = bestBoardsList)
    }

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
            boardPort.findBoardWithCursorAndTopic(boardsGet.cursorId, boardsGet.type, topic, boardsGet.pageable).toList()

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
        checkUserStatus(user)
        val topic = topicPort.getById(boardCreate.topicId)
        val board = Board.from(boardCreate, user, topic)
        return SaveBoardDto(boardPort.create(board).id)
    }

    @Transactional
    fun update(boardUpdate: BoardUpdate): SaveBoardDto {
        val user = getLoginUser(userPort)
        checkUserStatus(user)
        val board = getById(boardUpdate.boardId).update(boardUpdate)

        if (board.user.id != user.id) {
            throw ForbiddenException("자신의 글만 수정할 수 있습니다.")
        }

        return SaveBoardDto(boardPort.update(board).id)
    }

    private fun fetchCommentCount(boardId: Long) :Int {
        val commentCount = commentPort.countByBoardId(boardId)
        return if (commentCount > 0) commentCount else 0
    }

    private fun fetchWhiskyCount(boardId: Long): Int {
        val whiskyCount = whiskyPort.countByBoardId(boardId)
        return if (whiskyCount > 0) whiskyCount else 0
    }

    private fun checkUserStatus(user: User) {
        if (user.userStatus != UserStatus.ACTIVE) {
            throw ForbiddenException("게시글을 작성할 수 없습니다.")
        }
    }

}