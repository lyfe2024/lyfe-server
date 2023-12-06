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
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort


class GetBoardControllerTest(
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

    afterContainer {
        // 테스트 완료 후 데이터베이스 정리
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

        When("게시판을 조회 했을 때") {


            val res = testContainer.boardGetController.get(1L).result

            Then("저장된 게시판의 필드와 응답값 과 일치해야 한다") {
                res.title shouldBe req.title
                res.content shouldBe req.content
                res.boardType shouldBe req.boardType.toString()
                res.user.id shouldBe req.userId
            }
        }
    }

    Given("게시판 리스트 조회를 위한 데이터가 준비되었을 때") {

        val req = BoardSaveReq(
            title = "테스트 게시판 제목",
            content = "테스트 내용입니다. 여기에 게시판 내용이 들어갑니다.",
            boardType = BoardType.BOARD,
            userId = 1L,
            topicId = 1L
        )

        testContainer.boardCreateController.create(req)
        testContainer.boardCreateController.create(req)

        When("게시판 리스트를 조회 했을 때") {

            val res = testContainer.boardGetController.getBoards(1L , 10).result

            Then("저장된 게시판의 필드와 응답값 과 일치해야 한다") {
                res.forEach { board ->
                    board.title shouldBe req.title
                    board.content shouldBe req.content
                    board.boardType shouldBe req.boardType.toString()
                    board.user.id shouldBe req.userId //FIXME 데이터가 쌓인다? 초기화?
                }
            }
        }
    }
})