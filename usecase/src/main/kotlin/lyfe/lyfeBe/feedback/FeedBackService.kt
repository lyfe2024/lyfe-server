package lyfe.lyfeBe.feedback

import lyfe.lyfeBe.feedback.port.out.FeedBackPort
import lyfe.lyfeBe.user.port.out.UserPort
import org.springframework.stereotype.Service

@Service
class FeedBackService(
    private val feedBackRepository: FeedBackPort,
    private val userPort: UserPort
) {


    fun create(feedBackCreate: FeedBackCreate): Long {
        val user = userPort.getById(feedBackCreate.userId)
        val feedBack = Feedback(
            user = user,
            content = feedBackCreate.feedBack
        )

        return feedBackRepository.create(feedBack).id
    }

}