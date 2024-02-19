package lyfe.lyfeBe.web.board.req

import jakarta.validation.constraints.NotBlank


data class BoardUpdateRequest(
    @NotBlank
    val title: String,
    @NotBlank
    val content: String,

    val imageUrl: String? = null
)
