package br.com.wagner.workshopmongo.comentarioTest.unitario

import br.com.wagner.workshopmongo.comentarios.repository.ComentarioRepository
import br.com.wagner.workshopmongo.comentarios.request.InsertComentarioRequest
import br.com.wagner.workshopmongo.comentarios.service.InsertCometarioService
import br.com.wagner.workshopmongo.exceptions.ResourceNotFoundException
import br.com.wagner.workshopmongo.post.model.Post
import br.com.wagner.workshopmongo.post.repository.PostRepository
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
class InsertComentarioServiceTest {

    @field:InjectMocks
    lateinit var insertComentarioService: InsertCometarioService

    @field:Mock
    lateinit var postRepository: PostRepository

    @field:Mock
    lateinit var comentarioRepository: ComentarioRepository

    // 1 cenario de testes/ caminho feliz

    @Test
    fun `deve salvar um comentario`() {

        // cenario

        val idPostValido = UUID.randomUUID().toString()

        val author = User(
            id = UUID.randomUUID().toString(),
            name = "Marina",
            email = "marina@gmail.com")

        val post = Post(
            id = idPostValido,
            data = LocalDate.now(),
            title = "Vou a praia",
            body = "ainda hoje",
            author = author
        )

        val request = InsertComentarioRequest(
            idPost = idPostValido,
            text = "E perigoso",
            data = LocalDate.now()
        )

        val comentario = request.toModel(post)

        // ação

        // comportamento = deve retornar um post
        Mockito.`when`(postRepository.findById(idPostValido)).thenReturn(Optional.of(post))

        // comportamento = deve retornar um post
        Mockito.`when`(comentarioRepository.save(comentario)).thenReturn(comentario)

        // assertivas

        // nao deve lançar exceção
        Assertions.assertDoesNotThrow { insertComentarioService.insert(request) }
        Assertions.assertDoesNotThrow { comentarioRepository.save(comentario) }

        Mockito.verify(comentarioRepository, Mockito.times(1)).save(comentario)
    }

    // 1 cenario de testes/ caminho feliz

    @Test
    fun `deve lançar exception, ao tentar salvar comentario com id do post invalido`() {

        // cenario

        val idPostInValido = UUID.randomUUID().toString()

        val author = User(
            id = UUID.randomUUID().toString(),
            name = "Marina",
            email = "marina@gmail.com")

        val post = Post(
            id = idPostInValido,
            data = LocalDate.now(),
            title = "Vou a praia",
            body = "ainda hoje",
            author = author
        )

        val request = InsertComentarioRequest(
            idPost = idPostInValido,
            text = "E perigoso",
            data = LocalDate.now()
        )

        val comentario = request.toModel(post)

        // ação

        // comportamento = deve retornar um post
        Mockito.`when`(postRepository.findById(idPostInValido)).thenReturn(Optional.empty())

        // assertivas

        // nao deve lançar exceção
        Assertions.assertThrows(ResourceNotFoundException::class.java) {insertComentarioService.insert(request)}

        Mockito.verify(comentarioRepository, Mockito.times(0)).save(comentario)
    }

}