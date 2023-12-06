package initTest.lyfe.lyfeBe.test.board.controller

import initTest.lyfe.lyfeBe.test.mock.TestContainer
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import lyfe.lyfeBe.board.BoardType
import lyfe.lyfeBe.image.Image
import lyfe.lyfeBe.image.ImageType
import lyfe.lyfeBe.topic.Topic
import lyfe.lyfeBe.user.Role
import lyfe.lyfeBe.user.User
import lyfe.lyfeBe.user.UserStatus
import lyfe.lyfeBe.web.board.req.BoardSaveReq
import lyfe.lyfeBe.web.board.req.BoardUpdateReq


class UpdateBoardControllerTest(
) : BehaviorSpec({

    val testContainer = TestContainer.build()


    beforeContainer {

        val user = User(
            id = 1L,
            email = "testUser@example.com",
            hashedPassword = "hashedPassword",
            nickname = "testUser",
            notificationConsent = true,
            fcmRegistration = true,
            role = Role.USER,
            userStatus = UserStatus.ACTIVE,
        )
        testContainer.userRepository.create(user)

        val topic = Topic(1L, "testTopic", visibility = true)
        testContainer.topicRepository.create(topic)


        val image = Image(
            id = 1L,
            url = "https://example.com/image.jpg",
            board = null,
            user = user,
            type = ImageType.PROFILE,
            width = 100,
            height = 100
        )
        testContainer.imageRepository.create(image)

    }

    Given("게시판 생성을 위한 요청 데이터가 준비되었을 때") {

        val req = BoardSaveReq(
            title = "테스트 게시판 제목",
            content = "테스트 내용입니다. 여기에 게시판 내용이 들어갑니다.",
            boardType = BoardType.BOARD,
            userId = 1L,
            topicId = 1L
        )

        testContainer.boardCreateController.create(req)

        When("게시판을 업데이트 했을 때") {

            val updateReq = BoardUpdateReq(
                title = "바뀐  게시판 제목",
                content = "바뀐 테스트 내용입니다. 여기에 게시판 내용이 들어갑니다."
            )

            val boardId = testContainer.boardUpdateController.update(1L, updateReq)
            val board = testContainer.boardService.getById(boardId.result)

            Then("업데이트된 게시판의 제목과 내용이 요청된 값과 일치해야 한다") {
                board.title shouldBe updateReq.title
                board.content shouldBe updateReq.content
            }
        }
    }

})