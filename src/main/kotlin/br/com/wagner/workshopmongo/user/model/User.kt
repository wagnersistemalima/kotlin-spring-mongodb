package br.com.wagner.workshopmongo.user.model

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.time.LocalDateTime
import java.util.*

@Document(value = "user")
class User(

    @Id
    val id: String = UUID.randomUUID().toString(),

    val name: String,
    var email: String
){
    var dataRegistro = LocalDateTime.now()

    var updateRegistro: LocalDateTime? = null

    // metodo auxiliar para toda vez que atualizar um usuario, salvar o instante

    fun update() {
        updateRegistro = LocalDateTime.now()
    }

}



