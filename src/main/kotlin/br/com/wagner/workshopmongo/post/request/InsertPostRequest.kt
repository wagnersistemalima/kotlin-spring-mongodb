package br.com.wagner.workshopmongo.post.request

import br.com.wagner.workshopmongo.post.model.Post
import br.com.wagner.workshopmongo.user.model.User
import com.fasterxml.jackson.annotation.JsonFormat
import java.time.LocalDate
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull

data class InsertPostRequest(

    @field:NotNull
    @field:JsonFormat(pattern = "dd/MM/yyyy", shape = JsonFormat.Shape.STRING)
    val data: LocalDate,

    @field:NotBlank
    val title: String,

    @field:NotBlank
    val body: String,

    @field:NotBlank
    val idAuthor: String
) {
    // metodo para converter dados request em entidade

    fun toModel(author: User): Post {
        return Post(data = data, title = title, body = body, author = author)
    }


}
