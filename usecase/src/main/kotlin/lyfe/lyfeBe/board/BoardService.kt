package lyfe.lyfeBe.board

import lyfe.lyfeBe.board.dto.BoardDto
import lyfe.lyfeBe.board.out.BoardPort
import lyfe.lyfeBe.image.port.out.ImagePort
import lyfe.lyfeBe.topic.port.TopicPort
import lyfe.lyfeBe.user.port.out.UserPort
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service

@Service
class BoardService(
        private val boardRepository: BoardPort,
        private val userRepository: UserPort,
        private val topicRepository: TopicPort,
        private val imageRepository: ImagePort
) {


    fun get(boardGet: BoardGet): BoardDto {
        val board = getById(boardGet.id)
        val image = imageRepository.getByUserId(board.user.id)
        return BoardDto.toDto(board, image.url)
    }


    fun create(boardCreate: BoardCreate): Board {
        val user = userRepository.getById(boardCreate.userId)
        val topic = topicRepository.getById(boardCreate.topicId)
        val board = Board.from(boardCreate, user, topic)
        return boardRepository.create(board)
    }


    fun update(boardUpdate: BoardUpdate): Long {
        val board = getById(boardUpdate.boardId).update(boardUpdate)
        return boardRepository.update(board).id
    }

    fun getById(id: Long): Board {
        return boardRepository.findById(id)
    }


    fun getBoards(boardsGet: BoardsGet): List<BoardDto> {
        val pageable = createPageRequest(boardsGet.pageable)
        val boards = fetchBoards(pageable)
        return boards.map { board ->
            BoardDto.toDto(board, fetchImageUrl(board.user.id))
        }.toList()
    }


    private fun createPageRequest(pageable: Pageable): PageRequest {
        return PageRequest.of(pageable.pageNumber, pageable.pageSize, pageable.sort)
    }

    private fun fetchBoards(pageable: PageRequest): List<Board> {
        return boardRepository.findAll(pageable).toList()
    }

    private fun fetchImageUrl(userId: Long): String {
        return imageRepository.getByUserId(userId).url
    }

    fun List<Board>.getUserIds(): List<Long> {
        return this.map { it.user.id }
    }
}