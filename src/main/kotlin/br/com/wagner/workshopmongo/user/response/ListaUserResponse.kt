package br.com.wagner.workshopmongo.user.response

import br.com.wagner.workshopmongo.user.model.User

data class ListaUserResponse(
    val id: String,
    val name: String,
    val email: String
){
    constructor(user: User): this(user.id, user.name, user.email)
}
