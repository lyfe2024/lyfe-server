package lyfe.lyfeBe.board

import lyfe.lyfeBe.board.out.BoardPort
import lyfe.lyfeBe.topic.port.TopicPort
import lyfe.lyfeBe.user.port.out.UserPort
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Service

@Service
class BoardService(
    private val boardRepository: BoardPort,
    private val userRepository: UserPort,
    private val topicRepository: TopicPort
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