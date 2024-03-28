package initTest.lyfe.lyfeBe.test.comment.service

import initTest.lyfe.lyfeBe.test.board.BoardFactory.Companion.createTestBoard
import initTest.lyfe.lyfeBe.test.comment.CommentFactory.Companion.createTestComment
import initTest.lyfe.lyfeBe.test.mock.FakeBoardRepository
import initTest.lyfe.lyfeBe.test.mock.FakeCommentRepository
import initTest.lyfe.lyfeBe.test.mock.FakeTopicRepository
import initTest.lyfe.lyfeBe.test.mock.FakeUserRepository
import initTest.lyfe.lyfeBe.test.user.UserFactory
import initTest.lyfe.lyfeBe.test.user.UserFactory.Companion.createTestUser
import io.github.oshai.kotlinlogging.KotlinLogging
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import lyfe.lyfeBe.board.Board
import lyfe.lyfeBe.comment.Comment
import lyfe.lyfeBe.comment.CommentCreate
import lyfe.lyfeBe.comment.CommentGetsByBoard
import lyfe.lyfeBe.comment.CommentUpdate
import lyfe.lyfeBe.comment.service.CommentService
import lyfe.lyfeBe.topic.Topic
import lyfe.lyfeBe.user.User
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.security.core.context.SecurityContextHolder

class CommentServiceTest(
) : BehaviorSpec({

    val log = KotlinLogging.logger { }
    val fakeCommentRepository = FakeCommentRepository()
    val fakeUserRepository = FakeUserRepository()
    val fakeBoardRepository = FakeBoardRepository()
    val fakeTopicRepository = FakeTopicRepository()
    val commentService = CommentService(
        fakeCommentRepository,
        fakeUserRepository,
        fakeBoardRepository
    )

    lateinit var board: Board
    lateinit var topic: Topic
    lateinit var user: User
    lateinit var comment: Comment

    beforeContainer {

        user = createTestUser()
        fakeUserRepository.create(user)

        topic = Topic(0, "testTopic")
        fakeTopicRepository.create(topic)

        board = createTestBoard()
        fakeBoardRepository.create(board)

        comment = createTestComment()
        fakeCommentRepository.create(comment)

        UserFactory.setSecurityContextUser(user)
    }

    afterContainer {
        fakeCommentRepository.clear()
        fakeUserRepository.clear()
        fakeBoardRepository.clear()
        fakeTopicRepository.clear()
        SecurityContextHolder.clearContext()
    }

    Given("댓글 생성 요청과, 사용자, 보드, 주제가 준비되었을 때") {

        val commentCreate = CommentCreate(
            content = "테스트 댓글 내용입니다. 여기에 댓글 내용이 들어갑니다.",
            commentGroupId = null,
            boardId = 1L
        )


        When("댓글 생성 요청을 처리하면") {

            val newComment = commentService.create(commentCreate)
            log.info { "newComment: $newComment" }
            val newCommentDto = commentService.getById(newComment.id)

            Then("생성된 댓글의 속성과 요청이 일치해야 한다") {
                newCommentDto.content shouldBe commentCreate.content
                newCommentDto.commentGroupId shouldBe commentCreate.commentGroupId
            }

        }

        When("댓글을 업데이트 했을 때") {

            val comment = commentService.create(commentCreate)

            val updateReq = CommentUpdate(
                commentId = comment.id,
                content = "바뀐 댓글 내용입니다. 여기에 댓글 내용이 들어갑니다.",
            )

            val commentId = commentService.update(updateReq)

            val updatedComment = fakeCommentRepository.getById(commentId.id)

            Then("업데이트된 댓글의 속성과 요청이 일치해야 한다") {
                updatedComment.content shouldBe updateReq.content
            }
        }

        When("생성된 댓글의 상세 정보를 조회할 때") {

            val commentDto = commentService.getById(comment.id)

            Then("조회된 댓글의 상세 정보가 생성 요청과 일치하는지 확인할 때") {
                commentDto.content shouldBe "이것은 테스트 코멘트입니다."
                commentDto.commentGroupId shouldBe 1L
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

            val commentsDto = commentService.getCommentsWithCursorAndBoard(commentGetsByBoard).list

            Then("조회된 댓글의 상세 정보가 생성 요청과 일치하는지 확인할 때") {
                commentsDto[0].content shouldBe "이것은 테스트 코멘트입니다."
                commentsDto[0].commentGroupId shouldBe 1
                commentsDto[0].user.id shouldBe 1L
            }
        }

        When("유저가 생성했던 여러 댓글 정보를 조회할 때(id 내림차순)") {

            val commentsDto = commentService.getCommentsWithCursorAndUser(
                cursorId = cursorId,
            ).list

            Then("조회된 댓글의 상세 정보가 생성 요청과 일치하는지 확인할 때") {
                commentsDto[0].content shouldBe "이것은 테스트 코멘트입니다."
                commentsDto[0].commentGroupId shouldBe 1
                commentsDto[0].user.id shouldBe 1L
            }
        }
    }


})