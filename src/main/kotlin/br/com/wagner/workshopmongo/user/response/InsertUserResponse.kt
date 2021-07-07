package br.com.wagner.workshopmongo.user.response

import br.com.wagner.workshopmongo.user.model.User

data class InsertUserResponse(
    val id: String,
    val name: String
) {
    constructor(user: User): this(user.id, user.name)
}