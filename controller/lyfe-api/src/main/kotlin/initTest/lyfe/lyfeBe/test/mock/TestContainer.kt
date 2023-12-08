package initTest.lyfe.lyfeBe.test.mock

import lyfe.lyfeBe.board.BoardService
import lyfe.lyfeBe.board.out.BoardPort
import lyfe.lyfeBe.image.port.out.ImagePort
import lyfe.lyfeBe.topic.port.TopicPort
import lyfe.lyfeBe.user.port.out.UserPort
import lyfe.lyfeBe.web.board.BoardCreateController
import lyfe.lyfeBe.web.board.BoardGetController
import lyfe.lyfeBe.web.board.BoardUpdateController

class TestContainer(
    var boardCreateController: BoardCreateController,
    var boardUpdateController: BoardUpdateController,
    var boardGetController: BoardGetController,
    var boardService: BoardService,
    var boardRepository: BoardPort,
    var userRepository: UserPort,
    var topicRepository: TopicPort,
    var imageRepository: ImagePort

) {

    companion object {
        fun build(): TestContainer {
            val boardRepository = FakeBoardRepository()
            val userRepository = FakeUserRepository()
            val topicRepository = FakeTopicRepository()
            val imageRepository = FakeImageRepository()
            val fakeWhiskyRepository = FakeWhiskyRepository()
            val fakeCommentRepository = FakeCommentRepository()

            val boardService = BoardService(
                boardRepository,
                userRepository,
                topicRepository,
                imageRepository,
                fakeWhiskyRepository,
                fakeCommentRepository
            )

            return TestContainer(
                BoardCreateController(boardService),
                BoardUpdateController(boardService),
                BoardGetController(boardService),
                boardService,
                boardRepository,
                userRepository,
                topicRepository,
                imageRepository
            )
        }
    }
}



