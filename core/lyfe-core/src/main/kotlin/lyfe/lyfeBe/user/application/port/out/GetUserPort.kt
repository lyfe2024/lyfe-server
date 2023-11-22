package lyfe.lyfeBe.user.application.port.out

import lyfe.lyfeBe.user.domain.User

interface GetUserPort {

    fun getById(id: Long): User
    fun getByIdAndValidateActive(id: Long): User

}