package initTest.lyfe.lyfeBe.test.comment.service

import initTest.lyfe.lyfeBe.test.mock.FakeBoardRepository
import initTest.lyfe.lyfeBe.test.mock.FakeCommentRepository
import initTest.lyfe.lyfeBe.test.mock.FakeTopicRepository
import initTest.lyfe.lyfeBe.test.mock.FakeUserRepository
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import lyfe.lyfeBe.board.Board
import lyfe.lyfeBe.board.BoardType
import lyfe.lyfeBe.comment.*
import lyfe.lyfeBe.comment.service.CommentService
import lyfe.lyfeBe.topic.Topic
import lyfe.lyfeBe.user.Role
import lyfe.lyfeBe.user.User
import lyfe.lyfeBe.user.UserStatus
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import java.time.Instant

class CommentServiceTest(
) : BehaviorSpec({

    val fakeCommentRepository = FakeCommentRepository()
    val fakeUserRepository = FakeUserRepository()
    val fakeBoardRepository = FakeBoardRepository()
    val fakeTopicRepository = FakeTopicRepository()
    val commentService = CommentService(
        fakeCommentRepository,
        fakeUserRepository,
        fakeBoardRepository
    )

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
            socialId = "testSocialId",
            socialType = lyfe.lyfeBe.auth.SocialType.GOOGLE,
        )
        fakeUserRepository.create(user)

        val topic = Topic(0, "testTopic")
        fakeTopicRepository.create(topic)

        val board = Board(
            id = 1L,
            title = "테스트 게시물",
            content = "게시물 내용입니다.",
            boardType = BoardType.BOARD,
            user = user,
            topic = topic,
            createdAt = Instant.now(),
            updatedAt = Instant.now()
        )

        fakeBoardRepository.create(board)

        val comment = Comment(
            id = 2L,
            content = "이것은 테스트 코멘트입니다.",
            commentGroupId = 1,
            user = user,
            board = board,
            createdAt = Instant.now(),
            updatedAt = Instant.now()
        )

        fakeCommentRepository.create(comment)

    }

    afterContainer {
        fakeCommentRepository.clear()
        fakeUserRepository.clear()
        fakeBoardRepository.clear()
        fakeTopicRepository.clear()
    }

    Given("댓글 생성 요청과, 사용자, 보드, 주제가 준비되었을 때") {

        val commentCreate = CommentCreate(
            content = "테스트 댓글 내용입니다. 여기에 댓글 내용이 들어갑니다.",
            commentGroupId = null,
            userId = 1L,
            boardId = 1L
        )

        When("댓글 생성 요청을 처리하면") {

            val newComment = commentService.create(commentCreate)
            val newCommentDto = commentService.getById(newComment.id)

            Then("생성된 댓글의 속성과 요청이 일치해야 한다") {
                newCommentDto.content shouldBe commentCreate.content
                newCommentDto.commentGroupId shouldBe commentCreate.commentGroupId
                newCommentDto.user.id shouldBe commentCreate.userId
            }

        }

        When("댓글을 업데이트 했을 때") {

            val updateReq = CommentUpdate(
                commentId = 2L,
                content = "바뀐 댓글 내용입니다. 여기에 댓글 내용이 들어갑니다.",
                userId = 1L,
            )

            val commentId = commentService.update(updateReq)

            val updatedComment = fakeCommentRepository.getById(commentId.id)

            Then("업데이트된 댓글의 속성과 요청이 일치해야 한다") {
                updatedComment.content shouldBe updateReq.content
                updatedComment.user.id shouldBe updateReq.userId
            }
        }

        When("생성된 댓글의 상세 정보를 조회할 때") {

            val commentDto = commentService.getById(2L)

            Then("조회된 댓글의 상세 정보가 생성 요청과 일치하는지 확인할 때") {
                commentDto.content shouldBe "이것은 테스트 코멘트입니다."
                commentDto.commentGroupId shouldBe 1
                commentDto.user.id shouldBe 1L
            }
        }
    }

    Given("댓글 생성 복수 요청이 준비되고 실행되었을 때") {
        val cursorId = Long.MAX_VALUE

        val pageable = PageRequest.of(
            0, // 페이지 번호 (0부터 시작)
            5, // 페이지 크기
            Sort.by("id").descending()
        )

        val commentGetsByBoard = CommentGetsByBoard(
            boardId = 1L,
            cursorId = cursorId,
            pageable = pageable
        )

        When("한 게시물에 생성된 여러 댓글의 정보를 조회할 때(id 내림차순)") {

            val commentsDto = commentService.getCommentsWithCursorAndBoard(commentGetsByBoard)

            Then("조회된 댓글의 상세 정보가 생성 요청과 일치하는지 확인할 때") {
                commentsDto[0].content shouldBe "이것은 테스트 코멘트입니다."
                commentsDto[0].commentGroupId shouldBe 1
                commentsDto[0].user.id shouldBe 1L
            }
        }

        When("유저가 생성했던 여러 댓글 정보를 조회할 때(id 내림차순)") {

            val commentsDto = commentService.getCommentsWithCursorAndUser(
                CommentGetsByUserId(

                    cursorId = cursorId,
                    userId = 1L
                )
            )

            Then("조회된 댓글의 상세 정보가 생성 요청과 일치하는지 확인할 때") {
                commentsDto[0].content shouldBe "이것은 테스트 코멘트입니다."
                commentsDto[0].commentGroupId shouldBe 1
                commentsDto[0].user.id shouldBe 1L
            }
        }
    }


})