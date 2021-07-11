package br.com.wagner.workshopmongo.comentarios.request

import br.com.wagner.workshopmongo.comentarios.model.Comentario
import br.com.wagner.workshopmongo.post.model.Post
import com.fasterxml.jackson.annotation.JsonFormat
import java.time.LocalDate
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull
import javax.validation.constraints.Size

data class InsertComentarioRequest(

    @field:NotBlank
    val idPost: String,

    @field:NotBlank
    @field:Size(max = 400)
    val text: String,

    @field:NotNull
    @field:JsonFormat(pattern = "dd/MM/yyyy", shape = JsonFormat.Shape.STRING)
    val data: LocalDate
) {

    // metodo para converter dados request em entidade

    fun toModel(post: Post?): Comentario {

        return Comentario(post = post!!, text = text, data = data)
    }


}
