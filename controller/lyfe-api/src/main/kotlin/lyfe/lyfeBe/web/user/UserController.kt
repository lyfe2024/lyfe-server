package lyfe.lyfeBe.web.user

import lyfe.lyfeBe.board.BoardType
import lyfe.lyfeBe.board.dto.BoardDto
import lyfe.lyfeBe.dto.CommonResponse
import lyfe.lyfeBe.user.UserUpdate
import lyfe.lyfeBe.user.dto.UpdateUserDto
import lyfe.lyfeBe.user.service.UserService
import lyfe.lyfeBe.utils.ControllerUtils.Companion.getEffectiveCursorId
import lyfe.lyfeBe.web.user.req.UpdateUserRequest
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort
import org.springframework.data.web.PageableDefault
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/v1/users")
class UserController(
    private val userService: UserService,
) {

    @GetMapping("/me")
    fun getMe() = CommonResponse(
        userService.getMe()
    )

    @PutMapping("/me")
    fun updateMe(
        @RequestBody req: UpdateUserRequest
    ): CommonResponse<UpdateUserDto> {
        return CommonResponse(
            userService.update(
                UserUpdate(
                    nickname = req.nickname,
                    profileUrl = req.profileUrl,
                    width = req.width,
                    height = req.height
                )
            )
        )
    }

    @GetMapping("/me/boards")
    fun getMyBoardList(
        @RequestParam boardType: BoardType,
        @RequestParam(required = false) cursorId: Long?,
        @PageableDefault(size = 10, page = 0, sort = ["id"], direction = Sort.Direction.DESC) pageable: Pageable,
    ): CommonResponse<List<BoardDto>> {
        val boardId = getEffectiveCursorId(cursorId)
        return CommonResponse(
            userService.getMyBoardList(
                boardType = boardType,
                cursorId = boardId,
                pageable = pageable
            )
        )
    }


    @GetMapping("/check-nickname/{nickname}")
    fun checkNickname(
        @PathVariable nickname: String
    ): CommonResponse<CheckNicknameResponse> {
        userService.checkNickname(nickname)
        return CommonResponse(CheckNicknameResponse(true))
    }

}