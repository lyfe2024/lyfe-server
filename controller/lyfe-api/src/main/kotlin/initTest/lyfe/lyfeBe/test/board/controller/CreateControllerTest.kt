package initTest.lyfe.lyfeBe.test.board.controller

import initTest.lyfe.lyfeBe.test.mock.*
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import lyfe.lyfeBe.board.*
import lyfe.lyfeBe.image.Image
import lyfe.lyfeBe.image.ImageType
import lyfe.lyfeBe.topic.Topic
import lyfe.lyfeBe.user.Role
import lyfe.lyfeBe.user.User
import lyfe.lyfeBe.user.UserStatus
import lyfe.lyfeBe.web.board.req.BoardSaveReq
import lyfe.lyfeBe.web.board.req.BoardUpdateReq


class CreateControllerTest(
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



    Given("게시판 생성 요청이 준비되었을 때") {

        val req = BoardSaveReq(
            title = "테스트 게시판 제목",
            content = "테스트 내용입니다. 여기에 게시판 내용이 들어갑니다.",
            boardType = BoardType.BOARD,
            userId = 1L,
            topicId = 1L
        )

        val board = testContainer.boardCreateController.create(req)



        When("생성된 게시판의 제목, 내용, 유형이 요청과 일치해야 한다") {

            Then("해당 보드 타이틀과 본문이 일치한다.") {
                board.result.title shouldBe req.title
                board.result.content shouldBe req.content
                board.result.boardType shouldBe req.boardType
                board.result.user.id shouldBe req.userId
                board.result.topic.id shouldBe req.topicId
            }
        }
    }
})