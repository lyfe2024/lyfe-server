package initTest.lyfe.lyfeBe.test.board

import initTest.lyfe.lyfeBe.test.user.UserFactory
import lyfe.lyfeBe.board.*
import lyfe.lyfeBe.topic.Topic
import lyfe.lyfeBe.user.User
import lyfe.lyfeBe.web.board.req.BoardSaveRequest
import lyfe.lyfeBe.web.topic.TopicFactory
import org.springframework.data.domain.Pageable
import java.time.Instant
import java.time.LocalDate

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


        //Todo
        fun createBoardsGet(boardId: Long, pageable: Pageable, type: BoardType, date: LocalDate): BoardsGet {
            return BoardsGet(
                cursorId = boardId,
                date = date,
                type = type,
                pageable = pageable,
            )
        }

//        fun createPopularBoard(date: String,  type: BoardType, count: Int): BoardsPopularGet {
//            return BoardsPopularGet(
//                whiskyCount = CURSOR_VALUE,
//                date = date,
//                type = type,
//                count = count,
//            )
//        }
        fun createUserBoard(userId: Long, cursorId: Long, type: BoardType, pageable: Pageable): BoardsUserGet {
            return BoardsUserGet(
                cursorId = cursorId,
                type = type,
                pageable = pageable,
            )
        }


        fun createBoardsSaveRequest(
            title: String = "테스트 게시판 제목",
            content: String = "테스트 내용입니다.",
            boardType: BoardType = BoardType.BOARD,
            userId: Long = 1L,
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
