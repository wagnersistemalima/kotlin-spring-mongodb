package br.com.wagner.workshopmongo.postTest.unitario

import br.com.wagner.workshopmongo.exceptions.ResourceNotFoundException
import br.com.wagner.workshopmongo.post.model.Post
import br.com.wagner.workshopmongo.post.repository.PostRepository
import br.com.wagner.workshopmongo.post.response.BuscarPostResponse
import br.com.wagner.workshopmongo.post.service.BuscarPostIdService
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

@ExtendWith(SpringExtension::class)
class BuscarPostIdServiceTest {

    @field:InjectMocks
    lateinit var buscarPostIdService: BuscarPostIdService

    @field:Mock
    lateinit var postRepository: PostRepository

    // 1 cenario de teste/ caminho feliz

    @Test
    fun `deve buscar um post por id, retornar objeto de resposta`() {

        // cenario

        val idAuthorValido = UUID.randomUUID().toString()

        val idPostValido = UUID.randomUUID().toString()

        val author = User(
            id = idAuthorValido,
            name = "Wagner",
            email = "wagner@email.com"

        )

        val post = Post(
            id = idPostValido,
            data = LocalDate.now(),
            title = "vou a praia",
            body = "no ceara",
            author = author
        )

        val response = BuscarPostResponse(post)

        // ação

        // comportamento = deve retornar um post
        Mockito.`when`(postRepository.findById(idPostValido)).thenReturn(Optional.of(post))

        //assertvias

        // compara a resposta
        Assertions.assertEquals(response, buscarPostIdService.findById(idPostValido))

        // nao deve lançar exceptions
        Assertions.assertDoesNotThrow { buscarPostIdService.findById(idPostValido) }

        // verifica se foi chamado findById()
        Mockito.verify(postRepository, Mockito.times(2)).findById(idPostValido)

    }

    // 2 cenario de teste

    @Test
    fun `deve retornar exception, quando não encontrar o post`() {

        // cenario

        val idPostInValido = UUID.randomUUID().toString()

        // ação

        // comportamento = deve retornar um post
        Mockito.`when`(postRepository.findById(idPostInValido)).thenReturn(Optional.empty())

        //assertvias

        // deve lançar exceção
        Assertions.assertThrows(ResourceNotFoundException::class.java) {buscarPostIdService.findById(idPostInValido)}

        // verifica se foi chamado findById()

        Mockito.verify(postRepository, Mockito.times(1)).findById(idPostInValido)

    }
}