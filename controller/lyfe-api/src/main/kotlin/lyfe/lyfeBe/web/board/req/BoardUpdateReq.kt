package lyfe.lyfeBe.web.board.req

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import lyfe.lyfeBe.board.BoardType


data class BoardUpdateReq(
    @NotBlank
    val title: String,
    @NotBlank
    val content: String,
)
