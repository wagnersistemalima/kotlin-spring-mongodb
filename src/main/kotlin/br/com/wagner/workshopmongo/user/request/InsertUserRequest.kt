package br.com.wagner.workshopmongo.user.request

import br.com.wagner.workshopmongo.user.model.User
import javax.validation.constraints.Email
import javax.validation.constraints.NotBlank

data class InsertUserRequest(

    @field:NotBlank
    val name: String,

    @field:NotBlank
    @field:Email
    val email: String
) {

    // metodo para converter dados da requisição em entidade

    fun toModel(): User {

        return User(name = name, email = email)
    }
}
