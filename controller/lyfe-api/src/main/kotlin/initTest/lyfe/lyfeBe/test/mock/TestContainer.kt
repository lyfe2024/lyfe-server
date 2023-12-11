package initTest.lyfe.lyfeBe.test.mock

import lyfe.lyfeBe.board.service.BoardService
import lyfe.lyfeBe.board.port.out.BoardPort
import lyfe.lyfeBe.image.port.out.ImagePort
import lyfe.lyfeBe.topic.port.TopicPort
import lyfe.lyfeBe.user.port.out.UserPort
import lyfe.lyfeBe.web.board.*

class TestContainer(
    var createBoardController: CreateBoardController,
    var updateBoardController: UpdateBoardController,
    var getBoardController: GetBoardController,
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

            val createBoardController = CreateBoardController(boardService)
            val updateBoardController = UpdateBoardController(boardService)
            val getBoardController = GetBoardController(boardService)

            return TestContainer(
                createBoardController,
                updateBoardController,
                getBoardController,
                boardService,
                boardRepository,
                userRepository,
                topicRepository,
                imageRepository
            )
        }
    }
}



