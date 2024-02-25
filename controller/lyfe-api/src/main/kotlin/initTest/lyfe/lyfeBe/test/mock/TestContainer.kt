package initTest.lyfe.lyfeBe.test.mock

import lyfe.lyfeBe.board.port.out.BoardPort
import lyfe.lyfeBe.board.service.BoardService
import lyfe.lyfeBe.comment.port.out.CommentPort
import lyfe.lyfeBe.comment.service.CommentService
import lyfe.lyfeBe.fcm.FCMService
import lyfe.lyfeBe.fcm.NotificationController
import lyfe.lyfeBe.fcm.port.FcmPort
import lyfe.lyfeBe.report.port.out.ReportPort
import lyfe.lyfeBe.report.service.ReportService
import lyfe.lyfeBe.topic.port.TopicPort
import lyfe.lyfeBe.topic.port.TopicService
import lyfe.lyfeBe.user.port.out.UserPort
import lyfe.lyfeBe.web.board.BoardController
import lyfe.lyfeBe.web.comment.CommentController
import lyfe.lyfeBe.web.report.ReportController
import lyfe.lyfeBe.web.topic.TopicController
import lyfe.lyfeBe.web.whisky.WhiskyController
import lyfe.lyfeBe.whisky.WhiskyService
import lyfe.lyfeBe.whisky.out.WhiskyPort

class TestContainer(
    var boardController: BoardController,
    var topicController: TopicController,
    var commentController: CommentController,
    var notificationController: NotificationController,
    var whiskyController: WhiskyController,
    var reportController: ReportController,

    var boardService: BoardService,
    var boardRepository: BoardPort,
    var commentService: CommentService,
    var whiskyService: WhiskyService,
    var reportService: ReportService,

    var commentRepository: CommentPort,
    var userRepository: UserPort,
    var topicService: TopicService,
    var topicRepository: TopicPort,
    var fakeNotificationRepository: FcmPort,
    var whiskyRepository: WhiskyPort,
    var reportRepository: ReportPort,

) {
    companion object {
        fun build(): TestContainer {

            val boardRepository = FakeBoardRepository()
            val userRepository = FakeUserRepository()
            val topicRepository = FakeTopicRepository()
            val fakeWhiskyRepository = FakeWhiskyRepository()
            val commentRepository = FakeCommentRepository()
            val fakeNotificationRepository = FakeNotificationRepository()
            val reportRepository = FakeReportRepository()

            val boardService = BoardService(
                boardRepository,
                userRepository,
                topicRepository,
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

            val whiskyService = WhiskyService(
                fakeWhiskyRepository,
                userRepository,
                boardRepository
            )

            val reportService = ReportService(
                reportRepository,
                userRepository,
                boardRepository,
                commentRepository
            )


            val boardController = BoardController(boardService)
            val commentController = CommentController(commentService)
            val topicController = TopicController(topicService)
            val notificationController = NotificationController(fcmService)
            val whiskyController = WhiskyController(whiskyService)
            val reportController = ReportController(reportService)

            return TestContainer(
                boardController = boardController,
                topicController = topicController,
                commentController = commentController,
                notificationController = notificationController,
                whiskyController= whiskyController,
                reportController = reportController,

                boardService = boardService,
                commentService = commentService,
                whiskyService = whiskyService,
                topicService = topicService,
                reportService = reportService,

                boardRepository = boardRepository,
                commentRepository = commentRepository,
                userRepository = userRepository,
                topicRepository = topicRepository,
                fakeNotificationRepository = fakeNotificationRepository,
                whiskyRepository = fakeWhiskyRepository,
                reportRepository = reportRepository
            )
        }
    }
}



