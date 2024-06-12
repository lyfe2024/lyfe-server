package lyfe.lyfeBe.feedback.service

import lyfe.lyfeBe.auth.service.SecurityUtils.getLoginUserId
import lyfe.lyfeBe.feedback.Feedback
import lyfe.lyfeBe.feedback.FeedbackCreate
import lyfe.lyfeBe.feedback.dto.SaveFeedbackDto
import lyfe.lyfeBe.feedback.port.out.FeedbackPort
import lyfe.lyfeBe.user.port.out.UserPort
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Transactional(readOnly = true)
@Service
class FeedbackService(
    private val feedbackPort: FeedbackPort,
    private val userPort: UserPort,
) {

    @Transactional
    fun create(feedbackCreate: FeedbackCreate): SaveFeedbackDto {

        val user = userPort.getById(userId = getLoginUserId(userPort))

        val feedback = Feedback.from(feedbackCreate, user)

        return SaveFeedbackDto(feedbackPort.create(feedback).id)
    }
}