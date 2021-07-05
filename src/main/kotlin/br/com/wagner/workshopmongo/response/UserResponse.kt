package br.com.wagner.workshopmongo.response

import br.com.wagner.workshopmongo.user.model.User

data class UserResponse(
    val id: String,
    val name: String,
    val email: String
){
    constructor(user: User): this(user.id, user.name, user.email)
}
