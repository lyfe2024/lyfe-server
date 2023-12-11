package lyfe.lyfeBe.user.dto

import lyfe.lyfeBe.user.User

data class UserDTO(
    val id: Int,
    val username: String,
    val profile: String
) {
    companion object {
        fun from(user: User , url : String): UserDTO {
            return UserDTO(
                id = user.id.toInt(),
                username = user.nickname,
                profile = url
            )
        }
    }
}
