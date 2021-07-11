package br.com.wagner.workshopmongo.comentarioTest.integracao

import br.com.wagner.workshopmongo.comentarios.repository.ComentarioRepository
import br.com.wagner.workshopmongo.comentarios.request.InsertComentarioRequest
import br.com.wagner.workshopmongo.comentarios.service.InsertCometarioService
import br.com.wagner.workshopmongo.post.model.Post
import br.com.wagner.workshopmongo.post.repository.PostRepository
import br.com.wagner.workshopmongo.user.model.User
import br.com.wagner.workshopmongo.user.repository.UserRepository
import com.fasterxml.jackson.databind.ObjectMapper
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.data.mongo.AutoConfigureDataMongo
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import java.net.URI
import java.time.LocalDate
import java.util.*

@SpringBootTest
@AutoConfigureDataMongo
@AutoConfigureMockMvc
@ActiveProfiles("test")
class InsertComentarioControllerTest {

    @field:Autowired
    lateinit var insertComentarioService: InsertCometarioService

    @field:Autowired
    lateinit var comentarioRepository: ComentarioRepository

    @field:Autowired
    lateinit var postRepository: PostRepository

    @field:Autowired
    lateinit var authorRepository: UserRepository

    @field:Autowired
    lateinit var mockMvc: MockMvc

    @field:Autowired
    lateinit var objectMapper: ObjectMapper


    // rodar antes de cada teste
    @BeforeEach
    internal fun setUp() {

    }

    // rodar depois de cada teste
    @AfterEach
    internal fun tearDown() {
        postRepository.deleteAll()
        authorRepository.deleteAll()

    }

    // 1 cenario de testes/ caminho feliz

    @Test
    fun `deve retornar 200, salvar comentario`() {

        // cenario

        val author = User(
            name = "Marina",
            email = "marina@gmail.com"
        )
        authorRepository.save(author)

        val post = Post(
            data = LocalDate.now(),
            title = "Vou a praia",
            body = "ainda hoje",
            author = author
        )
        postRepository.save(post)

        val uri = URI("/posts/comentarios")

        val request = InsertComentarioRequest(post.id, text = "bem dificil", data = LocalDate.now())

        // ação

        mockMvc.perform(MockMvcRequestBuilders.post(uri)
            .contentType(MediaType.APPLICATION_JSON).content(toJson(request = request)))
            .andExpect(MockMvcResultMatchers.status().`is`(200))

    }

    // 2 cenario de testes

    @Test
    fun `deve retornar 404, ao tentar salvar comentario com id post invalido`() {

        // cenario

        val idPostInvalido = UUID.randomUUID().toString()

        val author = User(
            name = "Marina",
            email = "marina@gmail.com"
        )
        authorRepository.save(author)

        val post = Post(
            data = LocalDate.now(),
            title = "Vou a praia",
            body = "ainda hoje",
            author = author
        )
        postRepository.save(post)

        val uri = URI("/posts/comentarios")

        val request = InsertComentarioRequest(
            idPost = idPostInvalido,
            text = "E perigoso",
            data = LocalDate.now()
        )

        // ação

        mockMvc.perform(MockMvcRequestBuilders
            .post(uri)
            .contentType(MediaType.APPLICATION_JSON).content(toJson(request)))
            .andExpect(MockMvcResultMatchers.status().`is`(404))

        // assertivas
    }

    // 3 cenario de testes

    @Test
    fun `deve retornar 400, ao tentar salvar comentario com text vazio`() {

        // cenario

        val author = User(
            name = "Marina",
            email = "marina@gmail.com"
        )
        authorRepository.save(author)

        val post = Post(
            data = LocalDate.now(),
            title = "Vou a praia",
            body = "ainda hoje",
            author = author
        )
        postRepository.save(post)

        val uri = URI("/posts/comentarios")

        val request = InsertComentarioRequest(
            idPost = post.id,
            text = "",
            data = LocalDate.now()
        )

        // ação

        mockMvc.perform(MockMvcRequestBuilders
            .post(uri)
            .contentType(MediaType.APPLICATION_JSON).content(toJson(request)))
            .andExpect(MockMvcResultMatchers.status().`is`(400))

        // assertivas
    }

    // metodo para dessserializar objeto da request

    fun toJson(request: InsertComentarioRequest): String {
        return objectMapper.writeValueAsString(request)
    }
}