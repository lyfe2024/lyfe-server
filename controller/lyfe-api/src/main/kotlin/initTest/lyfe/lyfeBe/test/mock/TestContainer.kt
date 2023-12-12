package initTest.lyfe.lyfeBe.test.mock

import lyfe.lyfeBe.board.service.BoardService
import lyfe.lyfeBe.board.port.out.BoardPort
import lyfe.lyfeBe.comment.port.out.CommentPort
import lyfe.lyfeBe.comment.service.CommentService
import lyfe.lyfeBe.image.port.out.ImagePort
import lyfe.lyfeBe.topic.port.TopicPort
import lyfe.lyfeBe.topic.port.TopicService
import lyfe.lyfeBe.user.port.out.UserPort
import lyfe.lyfeBe.web.board.*
import lyfe.lyfeBe.web.topic.CreateTopicController
import lyfe.lyfeBe.web.topic.GetTopicController
import lyfe.lyfeBe.web.topic.UpdateTopicController
import lyfe.lyfeBe.web.comment.CreateCommentController
import lyfe.lyfeBe.web.comment.GetCommentController
import lyfe.lyfeBe.web.comment.UpdateCommentController

class TestContainer(
    var createBoardController: CreateBoardController,
    var updateBoardController: UpdateBoardController,
    var getBoardController: GetBoardController,
    var createTopicController: CreateTopicController,
    var updateTopicController: UpdateTopicController,
    var getTopicController: GetTopicController,
    var createCommentController: CreateCommentController,
    var updateCommentController: UpdateCommentController,
    var getCommentController: GetCommentController,
    var boardService: BoardService,
    var boardRepository: BoardPort,
    var commentService: CommentService,
    var commentRepository: CommentPort,
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
            val commentRepository = FakeCommentRepository()

            val boardService = BoardService(
                boardRepository,
                userRepository,
                topicRepository,
                imageRepository,
                fakeWhiskyRepository,
                commentRepository
            )

            val commentService = CommentService(
                commentRepository,
                userRepository,
                boardRepository
            )

            val topicService : TopicService = TopicService(
                topicRepository
            )

            val createBoardController = CreateBoardController(boardService)
            val updateBoardController = UpdateBoardController(boardService)
            val getBoardController = GetBoardController(boardService)
            val createCommentController = CreateCommentController(commentService)
            val updateCommentController = UpdateCommentController(commentService)
            val getCommentController = GetCommentController(commentService)

            val createTopicController = CreateTopicController(topicService)
            val updateTopicController = UpdateTopicController(topicService)
            val getTopicController = GetTopicController(topicService)

            return TestContainer(
                createBoardController = createBoardController,
                updateBoardController = updateBoardController,
                getBoardController = getBoardController,
                createTopicController,
                updateTopicController,
                getTopicController,
                createCommentController = createCommentController,
                updateCommentController = updateCommentController,
                getCommentController = getCommentController,
                boardService = boardService,
                boardRepository = boardRepository,
                commentService = commentService,
                commentRepository = commentRepository,
                userRepository = userRepository,
                topicRepository = topicRepository,
                imageRepository = imageRepository
            )
        }
    }
}


