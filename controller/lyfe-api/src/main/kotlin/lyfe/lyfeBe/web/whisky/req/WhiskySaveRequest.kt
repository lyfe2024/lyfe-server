package lyfe.lyfeBe.web.whisky.req

import jakarta.validation.constraints.NotNull


data class WhiskySaveRequest(

    @NotNull
    val userId: Long,
    @NotNull
    val boardId: Long
)
