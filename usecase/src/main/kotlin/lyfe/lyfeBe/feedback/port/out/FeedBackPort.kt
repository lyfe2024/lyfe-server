package lyfe.lyfeBe.feedback.port.out

import lyfe.lyfeBe.board.Board
import lyfe.lyfeBe.feedback.Feedback
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable

interface FeedBackPort {
    fun create(feedBack: Feedback) : Feedback
}