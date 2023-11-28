package lyfe.lyfeBe.user.port.out

import lyfe.lyfeBe.user.User

interface GetUserPort {

    fun getById(id: Long): User
    fun getByIdAndValidateActive(id: Long): User

}