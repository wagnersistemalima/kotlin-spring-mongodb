package br.com.wagner.workshopmongo.postTest.integracao

import br.com.wagner.workshopmongo.post.model.Post
import br.com.wagner.workshopmongo.post.repository.PostRepository
import br.com.wagner.workshopmongo.post.response.BuscarPostResponse
import br.com.wagner.workshopmongo.post.service.BuscarPostIdService
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
import org.springframework.web.util.UriComponentsBuilder
import java.time.LocalDate
import java.util.*

@SpringBootTest
@AutoConfigureDataMongo
@AutoConfigureMockMvc
@ActiveProfiles("test")
class BuscarPostIdControllerTest {

    @field:Autowired
    lateinit var postRepository: PostRepository

    @field:Autowired
    lateinit var authorRepository: UserRepository

    @field:Autowired
    lateinit var buscarPostIdService: BuscarPostIdService

    @field:Autowired
    lateinit var mockMvc: MockMvc

    @field:Autowired
    lateinit var objectMapper: ObjectMapper

    // rodar antes de cada teste

    @BeforeEach
    internal fun setUp() {
        postRepository.deleteAll()
        authorRepository.deleteAll()
    }

    // rodar depois de cada teste

    @AfterEach
    internal fun tearDown() {
        postRepository.deleteAll()
        authorRepository.deleteAll()
    }

    // 1 cenario de testes/ caminho feliz

    @Test
    fun `deve retornar 200, com detalhes do post ao buscar por idPost`() {

        // cenario

        val author = User(name = "Marina", email = "marina@gmail.com")
        authorRepository.save(author)

        val post = Post(
            data = LocalDate.now(),
            title = "Vou a salvador",
            body = "para o medico",
            author = author

        )
        postRepository.save(post)

        val uri = UriComponentsBuilder.fromUriString("/posts/{id}").buildAndExpand(post.id).toUri()

        // ação

        val response = buscarPostIdService.findById(post.id)

        mockMvc.perform(MockMvcRequestBuilders
            .get(uri)
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers.status().`is`(200))
            .andExpect(MockMvcResultMatchers.content().json(toJson(response)))

        //assertivas
    }

    // 2 cenario de testes

    @Test
    fun `deve retornar 404, quando não encontrar o id do post`() {

        // cenario

        val idPostInexistente = UUID.randomUUID().toString()

        val author = User(name = "Marina", email = "marina@gmail.com")
        authorRepository.save(author)

        val post = Post(
            data = LocalDate.now(),
            title = "Vou a salvador",
            body = "para o medico",
            author = author

        )
        postRepository.save(post)

        val uri = UriComponentsBuilder.fromUriString("/posts/{id}").buildAndExpand(idPostInexistente).toUri()

        // ação

        val response = buscarPostIdService.findById(post.id)

        mockMvc.perform(MockMvcRequestBuilders
            .get(uri)
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers.status().`is`(404))

        //assertivas
    }

    // metodo para desserializar objeto de resposta

    fun toJson(response: BuscarPostResponse): String {
        return objectMapper.writeValueAsString(response)
    }
}