package lyfe.lyfeBe.persistence.board

import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Transactional(readOnly = true)
@Component
class BoardPersistenceAdapter(
    private val boardRepository: BoardRepository
) {
}