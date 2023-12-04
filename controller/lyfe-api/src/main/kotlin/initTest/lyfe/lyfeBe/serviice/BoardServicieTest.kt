//package initTest.lyfe.lyfeBe.serviice
//
//import io.kotest.core.spec.style.FunSpec
//import io.kotest.matchers.shouldBe
//import lyfe.lyfeBe.board.Board
//import lyfe.lyfeBe.board.BoardCreate
//import lyfe.lyfeBe.board.BoardType
//import lyfe.lyfeBe.board.BoardUpdate
//import lyfe.lyfeBe.topic.Topic
//import lyfe.lyfeBe.user.Role
//import lyfe.lyfeBe.user.User
//import lyfe.lyfeBe.user.UserStatus
//
//
//class BoardTest(
//) : FunSpec({
//
//    test("BoardCraete_ 게시물을 생성 할 수 있다.") {
//
//        val boardCreate = BoardCreate(
//            "testTile",
//            "testContent",
//            BoardType.BOARD,
//            1L,
//            1L
//        )
//        val user = User(
//            1L,
//            "testName",
//            "testEmail",
//            "testPassword",
//            true,
//            true,
//            Role.USER,
//            UserStatus.ACTIVE,
//
//            )
//        val topic = Topic(
//            id = 1L,
//            content = "testTopic",
//            visibility = true
//        )
//
//
//        val board = Board.from(boardCreate, user, topic)
//
//        board.title shouldBe boardCreate.title
//        board.content shouldBe boardCreate.content
//        board.boardType shouldBe boardCreate.boardType
//        board.user.id shouldBe boardCreate.userId // User 객체에서 ID만 비교
//        board.topic.id shouldBe boardCreate.topicId // Topic 객체에서 ID만 비교
//
//    }
//
//
//})