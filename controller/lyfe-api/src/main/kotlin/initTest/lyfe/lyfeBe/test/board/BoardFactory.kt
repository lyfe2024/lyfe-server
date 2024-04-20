package initTest.lyfe.lyfeBe.test.board

import initTest.lyfe.lyfeBe.test.user.UserFactory
import lyfe.lyfeBe.board.Board
import lyfe.lyfeBe.board.BoardCreate
import lyfe.lyfeBe.board.BoardType
import lyfe.lyfeBe.board.BoardUpdate
import lyfe.lyfeBe.topic.Topic
import lyfe.lyfeBe.user.User
import lyfe.lyfeBe.web.board.req.BoardSaveRequest
import lyfe.lyfeBe.web.topic.TopicFactory
import java.time.Instant

class BoardFactory {


    companion object {

        fun createTestBoard(
            id: Long = 1L,
            title: String = "testTitle",
            content: String = "testContent",
            boardType: BoardType = BoardType.BOARD,
            user: User = UserFactory.createTestUser(),
            topic: Topic = TopicFactory.createTestTopic(),
            createdAt: Instant? = null,
            updatedAt: Instant? = null
        ): Board {
            return Board(
                id = id,
                title = title,
                content = content,
                boardType = boardType,
                imageUrl = null,
                user = user,
                topic = topic,
                createdAt = createdAt,
                updatedAt = updatedAt
            )
        }

        fun createBoardCreate(
            title: String = "testTitle",
            content: String = "testContent",
            boardType: BoardType = BoardType.BOARD,
            topicId: Long = 1L
        ): BoardCreate {
            return BoardCreate(
                title,
                content,
                boardType,
                topicId,
            )
        }

        fun createBoardUpdate(
            boardId: Long = 1L,
            title: String = "testTitle",
            content: String = "testContent",
            imageUrl: String = "https://example.com/image.jpg",
            userId: Long = 1L,

        ): BoardUpdate {
            return BoardUpdate(
                boardId = boardId,
                title = title,
                content = content,
                imageUrl = imageUrl,
            )
        }

        fun createBoardsSaveRequest(
            title: String = "테스트 게시판 제목",
            content: String = "테스트 내용입니다.",
            boardType: BoardType = BoardType.BOARD,
            topicId: Long = 1L
        ): BoardSaveRequest {
            return BoardSaveRequest(
                title = title,
                content = content,
                boardType = boardType,
                topicId = topicId
            )
        }

    }
}
