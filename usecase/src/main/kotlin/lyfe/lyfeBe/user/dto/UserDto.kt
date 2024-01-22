package lyfe.lyfeBe.user.dto

import lyfe.lyfeBe.user.User

data class UserDto(
    val id: Int,
    val username: String,
    val profile: String?
) {
    companion object {
        fun from(user: User): UserDto {
            return UserDto(
                id = user.id.toInt(),
                username = user.nickname,
                profile = "" // profile 이미지 어떻게 처리할지..?
            )
        }

        fun from(user: User, profileUrl: String): UserDto {
            return UserDto(
                id = user.id.toInt(),
                username = user.nickname,
                profile = profileUrl
            )
        }
    }
}
