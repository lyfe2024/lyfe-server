package lyfe.lyfeBe.board

import lyfe.lyfeBe.board.dto.BoardDto
import lyfe.lyfeBe.board.out.BoardPort
import lyfe.lyfeBe.image.ImageType
import lyfe.lyfeBe.image.port.out.ImagePort
import lyfe.lyfeBe.topic.port.TopicPort
import lyfe.lyfeBe.user.port.out.UserPort
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Service

@Service
class BoardService(
        private val boardRepository: BoardPort,
        private val userRepository: UserPort,
        private val topicRepository: TopicPort,
        private val imageRepository : ImagePort
) {


    fun get(boardGet: BoardGet): BoardDto {
        val board = getById(boardGet.id)
        val image = imageRepository.getByUserId(board.user.id)
        return BoardDto.from(board, image.url)
    }


    fun create(boardCreate: BoardCreate): Long {
        val user = userRepository.getById(boardCreate.userId)
        val topic = topicRepository.getById(boardCreate.topicId)
        val board = Board.from(boardCreate, user, topic)
        return boardRepository.create(board).id
    }


    fun update(boardUpdate: BoardUpdate): Long {
        val board = getById(boardUpdate.boardId).update(boardUpdate)
        return boardRepository.update(board).id
    }

    fun getById(id: Long): Board {
        return boardRepository.findById(id)
    }

    fun getBoards(boardsGet: BoardsGet): List<Board> {
        val paging = PageRequest.of(boardsGet.page, boardsGet.size, Sort.Direction.DESC)
        return boardRepository.findAll(paging).toList()
    }


}