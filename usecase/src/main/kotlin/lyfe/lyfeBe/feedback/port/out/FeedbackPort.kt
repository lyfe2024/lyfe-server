package lyfe.lyfeBe.feedback.port.out

import lyfe.lyfeBe.feedback.Feedback

interface FeedbackPort {
    fun create(feedback: Feedback): Feedback
}