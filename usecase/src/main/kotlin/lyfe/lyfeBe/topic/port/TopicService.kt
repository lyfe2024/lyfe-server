package lyfe.lyfeBe.topic.port

import lyfe.lyfeBe.topic.Topic
import lyfe.lyfeBe.topic.TopicCreate
import lyfe.lyfeBe.topic.TopicGet
import lyfe.lyfeBe.topic.TopicUpdate
import lyfe.lyfeBe.topic.dto.GetTopicDto
import lyfe.lyfeBe.topic.dto.SaveTopicDto
import org.springframework.stereotype.Service

@Service
class TopicService(
    private val topicPort: TopicPort,
) {

    fun create(topicCreate: TopicCreate) = SaveTopicDto.toDto(topicPort.create(Topic.from(topicCreate)))

    fun update(topicUpdate: TopicUpdate) {
        topicPort.update(Topic.from(topicUpdate))

    }

    fun get(topicGet: TopicGet) =
        GetTopicDto.toDto(topicPort.getById(topicGet.topicId))


//    fun get(boardGet: BoardGet): BoardDto {
//
//        val board = getById(boardGet.id)
//        val image = imageport.getByUserId(board.user.id)
//        val whiskyCount = fetchWhiskyCount(board.id)
//        val commentCount = fetchCommentCount(board.id)
//
//        val params = BoardDtoAssembly(board, image.url, whiskyCount, commentCount)
//
//        return BoardDto.toDto(params)
//    }
//
//    fun getBoards(boardsGet: BoardsGet): List<BoardDto> {
//        val boards = fetchBoards(boardsGet.boardId, boardsGet.size)
//
//        return boards.map { board ->
//            val image = fetchImageUrl(board.user.id)
//            val whiskyCount = fetchWhiskyCount(board.id)
//            val commentCount = fetchCommentCount(board.id)
//            val params = BoardDtoAssembly(board, image, whiskyCount, commentCount)
//
//            BoardDto.toDto(params)
//        }.toList()
//    }


//
//    fun update(boardUpdate: BoardUpdate): Long {
//        val board = getById(boardUpdate.boardId).update(boardUpdate)
//        return boardport.update(board).id
//    }
//
//    fun getById(id: Long): Board {
//        return boardport.findById(id)
//    }

}