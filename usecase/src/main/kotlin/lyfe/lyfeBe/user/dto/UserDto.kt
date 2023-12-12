package lyfe.lyfeBe.user.dto

import lyfe.lyfeBe.user.User

data class UserDto(
    val id: Int,
    val username: String,
    val profile: String
) {
    companion object {
        fun from(user: User , url : String): UserDto {
            return UserDto(
                id = user.id.toInt(),
                username = user.nickname,
                profile = url
            )
        }
    }
}
