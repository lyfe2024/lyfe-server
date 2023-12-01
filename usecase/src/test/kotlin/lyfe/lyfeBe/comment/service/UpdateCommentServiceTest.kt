package lyfe.lyfeBe.comment.service

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import io.mockk.clearMocks
import io.mockk.every
import io.mockk.mockk
import lyfe.lyfeBe.MockObject
import lyfe.lyfeBe.board.port.out.GetBoardPort
import lyfe.lyfeBe.comment.port.out.GetCommentPort
import lyfe.lyfeBe.comment.port.out.SaveCommentPort
import lyfe.lyfeBe.error.ForbiddenException
import lyfe.lyfeBe.user.port.out.GetUserPort

internal class UpdateCommentServiceTest : BehaviorSpec({

    val saveCommentPort = mockk<SaveCommentPort>()
    val getCommentPort = mockk<GetCommentPort>()

    val updateCommentService = UpdateCommentService(
        saveCommentPort = saveCommentPort,
        getCommentPort = getCommentPort
    )

    beforeTest {
        clearMocks(saveCommentPort, getCommentPort)
    }

    Given("댓글이 존재하는 상황에서") {

        val comment = MockObject.testComment()
        val user = MockObject.testUser(nickname = "test")

        When("댓글 수정 - 성공") {

            every { saveCommentPort.update(any()) } returns comment
            every { getCommentPort.getById(any()) } returns comment

            val result = updateCommentService.update(
                commentId = comment.id,
                content = comment.content,
                userId = user.id
            )

            Then("댓글이 성공적으로 수정되었음 - 200 반환") {
                result.content shouldBe comment.content
                result.commentGroupId shouldBe comment.commentGroupId
                result.user shouldBe user
                result.board shouldBe comment.board

            }
        }

        When("댓글 수정 - 실패") {

            every { saveCommentPort.update(any()) } returns comment
            every { getCommentPort.getById(any()) } returns comment

            val exception = shouldThrow<ForbiddenException> {
                updateCommentService.update(
                    commentId = comment.id,
                    content = comment.content,
                    userId = 1L
                )
            }

            Then("자신의 댓글이 아님 - 401 반환") {
                exception.message shouldBe "자신의 댓글만 수정할 수 있습니다."
            }
        }
    }

})
