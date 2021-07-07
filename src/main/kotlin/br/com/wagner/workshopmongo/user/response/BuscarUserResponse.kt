package br.com.wagner.workshopmongo.user.response

import br.com.wagner.workshopmongo.user.model.User
import java.time.LocalDateTime

data class BuscarUserResponse(

    val id: String,
    val name: String,
    val email: String,
    val dataRegistro: LocalDateTime
){
    constructor(user: User): this(user.id, user.name, user.email, user.dataRegistro)
}