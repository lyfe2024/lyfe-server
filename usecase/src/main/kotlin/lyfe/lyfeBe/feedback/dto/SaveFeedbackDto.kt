package lyfe.lyfeBe.feedback.dto

import lyfe.lyfeBe.feedback.Feedback

data class SaveFeedbackDto(
    val id : Long,
){
    companion object {
        fun from(feedback : Feedback): SaveFeedbackDto {
            return SaveFeedbackDto(
                id = feedback.id,
            )
        }
    }
}
