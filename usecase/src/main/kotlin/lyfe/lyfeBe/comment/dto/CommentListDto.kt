package lyfe.lyfeBe.comment.dto

data class CommentListDto(
    val list : List<CommentDto>
){
    companion object {
        fun toListDto(list : List<CommentDto>) : CommentListDto {
            return CommentListDto(list)
        }
    }
}
