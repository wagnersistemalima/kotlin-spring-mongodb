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
    val email: String
){
    var dataRegistro = LocalDateTime.now()

}



