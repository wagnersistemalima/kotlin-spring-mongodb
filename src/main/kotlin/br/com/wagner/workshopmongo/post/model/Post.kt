package br.com.wagner.workshopmongo.post.model

import br.com.wagner.workshopmongo.user.model.User
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.time.LocalDate
import java.util.*

@Document(value = "post")
class Post(

    @Id
    var id: String = UUID.randomUUID().toString(),

    val data: LocalDate,

    val title: String,

    val body: String,

    val author: User
)
