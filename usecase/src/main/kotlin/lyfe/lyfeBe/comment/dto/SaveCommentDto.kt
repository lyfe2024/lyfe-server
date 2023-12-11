package lyfe.lyfeBe.comment.dto

import lyfe.lyfeBe.comment.Comment

data class SaveCommentDto(
    val id : Long,
){
    companion object {
        fun from(comment : Comment): SaveCommentDto {
            return SaveCommentDto(
                id = comment.id,
            )
        }
    }
}
