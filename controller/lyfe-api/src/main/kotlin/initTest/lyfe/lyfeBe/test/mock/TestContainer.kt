package initTest.lyfe.lyfeBe.test.mock

import lyfe.lyfeBe.board.BoardService
import lyfe.lyfeBe.board.out.BoardPort
import lyfe.lyfeBe.image.port.out.ImagePort
import lyfe.lyfeBe.topic.port.TopicPort
import lyfe.lyfeBe.topic.port.TopicService
import lyfe.lyfeBe.user.port.out.UserPort
import lyfe.lyfeBe.web.board.*
import lyfe.lyfeBe.web.topic.CreateTopicController
import lyfe.lyfeBe.web.topic.GetTopicController
import lyfe.lyfeBe.web.topic.UpdateTopicController

class TestContainer(
    var createBoardController: CreateBoardController,
    var updateBoardController: UpdateBoardController,
    var getBoardController: GetBoardController,
    var createTopicController: CreateTopicController,
    var updateTopicController: UpdateTopicController,
    var getTopicController: GetTopicController,
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

            val topicService : TopicService = TopicService(
                topicRepository
            )

            val createBoardController = CreateBoardController(boardService)
            val updateBoardController = UpdateBoardController(boardService)
            val getBoardController = GetBoardController(boardService)

            val createTopicController = CreateTopicController(topicService)
            val updateTopicController = UpdateTopicController(topicService)
            val getTopicController = GetTopicController(topicService)

            return TestContainer(
                createBoardController,
                updateBoardController,
                getBoardController,
                createTopicController,
                updateTopicController,
                getTopicController,
                boardService,
                boardRepository,
                userRepository,
                topicRepository,
                imageRepository
            )
        }
    }
}



