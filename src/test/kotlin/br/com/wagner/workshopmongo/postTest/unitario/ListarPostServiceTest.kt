package br.com.wagner.workshopmongo.postTest.unitario

import br.com.wagner.workshopmongo.post.model.Post
import br.com.wagner.workshopmongo.post.repository.PostRepository
import br.com.wagner.workshopmongo.post.response.BuscarPostResponse
import br.com.wagner.workshopmongo.post.service.ListarPostService
import br.com.wagner.workshopmongo.user.model.User
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito
import org.springframework.test.context.junit.jupiter.SpringExtension
import java.time.LocalDate
import java.util.*
import java.util.stream.Collectors

@ExtendWith(SpringExtension::class)
class ListarPostServiceTest {

    @field:InjectMocks
    lateinit var buscarPostService: ListarPostService

    @field:Mock
    lateinit var postRepository: PostRepository

    // 1 cenario de test/ caminhi feliz

    @Test
    fun `deve retornar uma lista de post, ao buscar o post pelo id do author`() {

        // cenario

        val idAuthorValido = UUID.randomUUID().toString()

        val author = User(
            id = idAuthorValido,
            name = "Wagner",
            email = "wagner@email.com"

        )

        val post1 = Post(
            id = UUID.randomUUID().toString(),
            data = LocalDate.now(),
            title = "vou a praia",
            body = "no ceara",
            author = author
        )

        val post2 = Post(
            id = UUID.randomUUID().toString(),
            data = LocalDate.now(),
            title = "vou a igreja",
            body = "no crato",
            author = author
        )

        val lista = mutableListOf<Post>()
        lista.add(post1)
        lista.add(post2)

        val response = lista.stream().map { post -> BuscarPostResponse(post) }.collect(Collectors.toList())

        // ação

        // comportamento = deve retornar uma lista de posters do autor
        Mockito.`when`(postRepository.findByAuthorId(idAuthorValido)).thenReturn(lista)


        // assertiva

        // espera o retorno da chamada do metodo
        Assertions.assertEquals(response, buscarPostService.findById(idAuthorValido))

        // verifica a chamada do metodo findByAuthorId()
        Mockito.verify(postRepository, Mockito.times(1)).findByAuthorId(idAuthorValido)
    }
}