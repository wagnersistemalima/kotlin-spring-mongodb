package br.com.wagner.workshopmongo.post.response

import br.com.wagner.workshopmongo.post.model.Post
import java.time.LocalDate

data class BuscarPostResponse(

    var id: String?,

    val data: LocalDate?,

    val title: String?,

    val body: String?,

    val idAutor: String?,

    val name: String?
){
    constructor(post: Post): this(post.id,post.data, post.title, post.body, post.author.id, post.author.name )
}


