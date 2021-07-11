package br.com.wagner.workshopmongo.comentarios.model

import br.com.wagner.workshopmongo.post.model.Post
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.time.LocalDate
import java.util.*

@Document(value = "comentario")
class Comentario(

    @Id
    val id: String = UUID.randomUUID().toString(),

    val post: Post,

    val text: String,

    val data: LocalDate
)
