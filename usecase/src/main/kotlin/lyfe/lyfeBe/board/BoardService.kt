package lyfe.lyfeBe.board

import lyfe.lyfeBe.board.port.BoardRepository
import lyfe.lyfeBe.topic.port.TopicRepository
import lyfe.lyfeBe.user.port.UserRepository
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Service

@Service
class BoardService(
    private val boardRepository: BoardRepository,
    private val userRepository: UserRepository,
    private val topicRepository: TopicRepository
) {


    fun get(boardGet: BoardGet) = getById(boardGet.id)


    fun create(boardCreate: BoardCreate): Board {
        val user = userRepository.getById(boardCreate.userId)
        val topic = topicRepository.getById(boardCreate.topicId)
        val board = Board.from(boardCreate, user, topic)
        return boardRepository.create(board);
    }


    fun update(boardUpdate: BoardUpdate) {
        val board = getById(boardUpdate.boardId).update(boardUpdate)
        boardRepository.update(board)
    }

    fun getById(id: Long): Board {
        return boardRepository.findById(id)
            .orElseThrow()
    }

    fun getBoards(boardsGet: BoardsGet): List<Board> {

        val paging = PageRequest.of(boardsGet.page, boardsGet.size, Sort.Direction.DESC)
        return boardRepository.findAll(paging).toList()
    }


}