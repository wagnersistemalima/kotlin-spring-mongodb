package br.com.wagner.workshopmongo.postTest.unitario

import br.com.wagner.workshopmongo.exceptions.GenericValidationException
import br.com.wagner.workshopmongo.post.model.Post
import br.com.wagner.workshopmongo.post.repository.PostRepository
import br.com.wagner.workshopmongo.post.request.InsertPostRequest
import br.com.wagner.workshopmongo.post.service.InsertPostService
import br.com.wagner.workshopmongo.user.model.User
import br.com.wagner.workshopmongo.user.repository.UserRepository
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
class InsertPostServiceTest {

    @field:InjectMocks
    lateinit var insertPostService: InsertPostService

    @field:Mock
    lateinit var postRepository: PostRepository

    @field:Mock
    lateinit var userRepository: UserRepository

    // 1 cenario de testes/ caminho feliz

    @Test
    fun `deve salvar um post, não deve lançar exceptions`() {

        // cenario

        val request = InsertPostRequest(
            data = LocalDate.now(),
            title = "vou a praia",
            body = "de Joao Pessoa",
            idAuthor = UUID.randomUUID().toString()
        )

        val user = User(name = "Wagner", email = "wagner@gmail.com")

        val post = request.toModel(user)

        //ação

        // comportamento = deve retornar um author
        Mockito.`when`(userRepository.findById(request.idAuthor)).thenReturn(Optional.of(user))

        // comportamento = deve retornar um post depois de salvar
        Mockito.`when`(postRepository.save(post)).thenReturn(post)

        //assertivas

        // nao deve lançar exception
        Assertions.assertDoesNotThrow { insertPostService.insert(request) }
        Assertions.assertDoesNotThrow { postRepository.save(post) }

        // verifica se save foi chamado
        Mockito.verify(postRepository, Mockito.times(1)).save(post)
    }

    // 2 cenario de testes

    @Test
    fun `deve salvar lançar exceptions, ao tentar inserir post com id autor invalido`() {

        // cenario

        val request = InsertPostRequest(
            data = LocalDate.now(),
            title = "vou a praia",
            body = "de Joao Pessoa",
            idAuthor = UUID.randomUUID().toString()
        )

        //ação

        // comportamento = deve retornar vazio
        Mockito.`when`(userRepository.findById(request.idAuthor)).thenReturn(Optional.empty())

        //assertivas

        // deve lançar exception
        Assertions.assertThrows(GenericValidationException::class.java) {insertPostService.insert(request)}


    }


}