package lyfe.lyfeBe.user.dto

import lyfe.lyfeBe.user.User

data class SaveUserDto(
    val id: Long,
){
    companion object {
        fun from(user: User): SaveUserDto {
            return SaveUserDto(
                id = user.id,
            )
        }
    }
}
