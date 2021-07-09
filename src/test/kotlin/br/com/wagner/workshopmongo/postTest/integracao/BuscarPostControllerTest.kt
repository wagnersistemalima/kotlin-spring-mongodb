package br.com.wagner.workshopmongo.postTest.integracao

import br.com.wagner.workshopmongo.post.model.Post
import br.com.wagner.workshopmongo.post.repository.PostRepository
import br.com.wagner.workshopmongo.post.response.BuscarPostResponse
import br.com.wagner.workshopmongo.post.service.BuscarPostService
import br.com.wagner.workshopmongo.user.model.User
import br.com.wagner.workshopmongo.user.repository.UserRepository
import com.fasterxml.jackson.databind.ObjectMapper
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions
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

@SpringBootTest
@AutoConfigureDataMongo
@AutoConfigureMockMvc
@ActiveProfiles("test")
class BuscarPostControllerTest {

    @field:Autowired
    lateinit var postRepository: PostRepository

    @field:Autowired
    lateinit var userRepository: UserRepository

    @field:Autowired
    lateinit var buscarPostService: BuscarPostService

    @field:Autowired
    lateinit var mockMvc: MockMvc

    @field:Autowired
    lateinit var objectMapper: ObjectMapper


    // rodar antes de cada teste
    @BeforeEach
    internal fun setUp() {
        postRepository.deleteAll()
        userRepository.deleteAll()
    }

    // rodar depois de cada teste
    @AfterEach
    internal fun tearDown() {
        postRepository.deleteAll()
        userRepository.deleteAll()
    }

    // 1 cenario de teste/ caminho feliz

    @Test
    fun `deve retornar 200, com uma lista de post quando pesquisado pelo id author valido`() {

        // cenario

        val author = User(name = "Wagner", email = "wagner@gmail.com")
        userRepository.save(author)

        val uri = UriComponentsBuilder.fromUriString("/users/{id}").buildAndExpand(author.id).toUri()

        val post1 = Post(
            data = LocalDate.now(),
            title = "vou a praia",
            body = "no ceara",
            author = author
        )
        postRepository.save(post1)

        val post2 = Post(
            data = LocalDate.now(),
            title = "vou a igreja",
            body = "no crato",
            author = author
        )
        postRepository.save(post2)

        // açao

        val list = postRepository.findByAuthorId(author.id)

        val buscarPostResponse = buscarPostService.findById(author.id)

        mockMvc.perform(MockMvcRequestBuilders
            .get(uri)
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers.status().`is`(200))

        // assertivas

        Assertions.assertEquals(2, buscarPostResponse.size)
        Assertions.assertEquals(2, list.size)
    }

    // metodo para desserializar objeto de resposta

    fun toJson(response: MutableList<BuscarPostResponse>): String {
        return objectMapper.writeValueAsString(response)
    }
}