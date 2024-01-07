package initTest.lyfe.lyfeBe.test.mock

import lyfe.lyfeBe.board.port.out.BoardPort
import lyfe.lyfeBe.board.service.BoardService
import lyfe.lyfeBe.comment.port.out.CommentPort
import lyfe.lyfeBe.comment.service.CommentService
import lyfe.lyfeBe.fcm.FCMService
import lyfe.lyfeBe.fcm.NotificationController
import lyfe.lyfeBe.fcm.port.FcmPort
import lyfe.lyfeBe.image.port.out.ImagePort
import lyfe.lyfeBe.topic.port.TopicPort
import lyfe.lyfeBe.topic.port.TopicService
import lyfe.lyfeBe.user.port.out.UserPort
import lyfe.lyfeBe.web.board.BoardController
import lyfe.lyfeBe.web.comment.CommentController
import lyfe.lyfeBe.web.topic.TopicController

class TestContainer(
    var boardController: BoardController,
    var topicController: TopicController,
    var commentController: CommentController,
    var notificationController: NotificationController,
    var boardService: BoardService,
    var boardRepository: BoardPort,
    var commentService: CommentService,
    var fcmService: FCMService,
    var commentRepository: CommentPort,
    var userRepository: UserPort,
    var topicService: TopicService,
    var topicRepository: TopicPort,
    var imageRepository: ImagePort,
    var fakeNotificationRepository: FcmPort

) {
    companion object {
        fun build(): TestContainer {

            val boardRepository = FakeBoardRepository()
            val userRepository = FakeUserRepository()
            val topicRepository = FakeTopicRepository()
            val imageRepository = FakeImageRepository()
            val fakeWhiskyRepository = FakeWhiskyRepository()
            val commentRepository = FakeCommentRepository()
            val fakeNotificationRepository = FakeNotificationRepository()

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

            val topicService = TopicService(
                topicRepository
            )

            val fcmService = FCMService(
                fakeNotificationRepository,
                userRepository
            )

            val boardController = BoardController(boardService)
            val commentController = CommentController(commentService)

            val topicController = TopicController(topicService)

            val notificationController = NotificationController(fcmService)

            return TestContainer(
                boardController = boardController,
                topicController = topicController,
                commentController = commentController,
                notificationController = notificationController,
                boardService = boardService,
                boardRepository = boardRepository,
                commentService = commentService,
                fcmService = fcmService,
                commentRepository = commentRepository,
                userRepository = userRepository,
                topicService = topicService,
                topicRepository = topicRepository,
                imageRepository = imageRepository,
                fakeNotificationRepository = fakeNotificationRepository
            )
        }
    }
}



