package lyfe.lyfeBe.web.board.req

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import lyfe.lyfeBe.board.BoardType


data class BoardSaveRequest(
    @NotBlank
    val title: String,
    @NotBlank
    val content: String,
    @NotNull
    val boardType: BoardType,
    @NotNull
    val userId : Long,
    @NotNull
    val topicId : Long
)
