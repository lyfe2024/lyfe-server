package lyfe.lyfeBe.comment.service

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import io.mockk.clearMocks
import io.mockk.every
import io.mockk.mockk
import lyfe.lyfeBe.MockObject
import lyfe.lyfeBe.board.port.out.GetBoardPort
import lyfe.lyfeBe.comment.port.out.GetCommentPort
import lyfe.lyfeBe.comment.port.out.SaveCommentPort
import lyfe.lyfeBe.user.port.out.GetUserPort

internal class CreateCommentServiceTest : BehaviorSpec({

    val saveCommentPort = mockk<SaveCommentPort>()
    val getUserPort = mockk<GetUserPort>()
    val getBoardPort = mockk<GetBoardPort>()
    val getCommentPort = mockk<GetCommentPort>()

    val createCommentService = CreateCommentService(
        saveCommentPort = saveCommentPort,
        getUserPort = getUserPort,
        getBoardPort = getBoardPort,
        getCommentPort = getCommentPort
    )

    beforeTest {
        clearMocks(saveCommentPort, getUserPort, getCommentPort)
    }

    Given("게시글이 존재하는 상황에서") {

        val comment = MockObject.testComment()
        val user = MockObject.testUser(nickname = "test")
        val commentByGroup = MockObject.testComment(commentGroupId = 1L)
        val commentByGroup2 = MockObject.testComment(commentGroupId = 2L)

        When("댓글 생성 - 성공") {

            every { saveCommentPort.create(any()) } returns comment
            every { getUserPort.getById(any()) } returns user
            every { getBoardPort.getById(any()) } returns mockk()

            val result = createCommentService.create(
                content = comment.content,
                commentGroupId = comment.commentGroupId,
                userId = user.id,
                boardId = 1L
            )

            Then("댓글이 성공적으로 생성되었음 - 200 반환") {
                result.content shouldBe comment.content
                result.commentGroupId shouldBe comment.commentGroupId
                result.user shouldBe user
                result.board shouldBe comment.board

            }
        }

        When("대댓글 생성 - 성공") {

            every { saveCommentPort.create(any()) } returns commentByGroup
            every { getUserPort.getById(any()) } returns user
            every { getBoardPort.getById(any()) } returns mockk()
            every { getCommentPort.getById(any()) } returns comment

            // When
            val result = createCommentService.create(
                content = commentByGroup.content,
                commentGroupId = commentByGroup.commentGroupId,
                userId = user.id,
                boardId = 1L
            )

            Then("대댓글이 성공적으로 생성되었음 - 200 반환") {
                result.content shouldBe commentByGroup.content
                result.commentGroupId shouldBe commentByGroup.commentGroupId
                result.user shouldBe user
                result.board shouldBe commentByGroup.board
            }
        }

        When("대댓글에 대댓글을 작성하려고 할 때 - 실패") {

            every { saveCommentPort.create(any()) } returns commentByGroup2
            every { getUserPort.getById(any()) } returns user
            every { getBoardPort.getById(any()) } returns mockk()
            every { getCommentPort.getById(any()) } returns commentByGroup

            val exception = shouldThrow<IllegalArgumentException> {
                createCommentService.create(
                    content = commentByGroup2.content,
                    commentGroupId = commentByGroup2.commentGroupId,
                    userId = user.id,
                    boardId = 1L
                )
            }

            Then("대댓글에 대댓글을 달 수 없습니다. - 400") {
                exception.message shouldBe "대댓글은 대댓글을 달 수 없습니다."
            }
        }
    }
})