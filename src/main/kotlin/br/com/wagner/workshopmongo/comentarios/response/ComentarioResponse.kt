package br.com.wagner.workshopmongo.comentarios.response

import br.com.wagner.workshopmongo.comentarios.model.Comentario
import java.time.LocalDate

data class ComentarioResponse(

    val idPost: String,

    val data: LocalDate
){
    constructor(comentario: Comentario): this(comentario.post.id, comentario.data)
}
