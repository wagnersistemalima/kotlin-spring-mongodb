package br.com.wagner.workshopmongo.postTest.integracao

import br.com.wagner.workshopmongo.post.request.InsertPostRequest
import br.com.wagner.workshopmongo.post.service.InsertPostService
import br.com.wagner.workshopmongo.user.model.User
import br.com.wagner.workshopmongo.user.repository.UserRepository
import com.fasterxml.jackson.databind.ObjectMapper
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mock
import org.mockito.Mockito
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
class InsertPostControllerTest {

    @field:Autowired
    lateinit var inserPostService: InsertPostService

    @field:Autowired
    lateinit var mockMvc: MockMvc

    @field:Autowired
    lateinit var objectMapper: ObjectMapper

    @field:Autowired
    lateinit var userRepository: UserRepository


    // rodar antes de cada teste

    @BeforeEach
    internal fun setUp() {
        userRepository.deleteAll()
    }

    // rodar depois de cada teste

    @AfterEach
    internal fun tearDown() {
        userRepository.deleteAll()
    }

    // 1 cenario de teste / caminho feliz

    @Test
    fun `deve retornar 200, cadastrar post`() {

        // cenario

        val uri = URI("/posts")

        val user = User(name = "Marina", email = "marina@gmail.com")
        userRepository.save(user)

        val request = InsertPostRequest(
            data = LocalDate.now(),
            title = "vou a praia",
            body = "de Joao Pessoa",
            idAuthor = user.id
        )

        //ação


        mockMvc.perform(MockMvcRequestBuilders
            .post(uri)
            .contentType(MediaType.APPLICATION_JSON)
            .content(toJson(request)))
            .andExpect(MockMvcResultMatchers.status().`is`(200))

        //assertivas
    }

    // 2 cenario de teste

    @Test
    fun `deve retornar 400, id do author invalido`() {

        // cenario

        val uri = URI("/posts")

        val request = InsertPostRequest(
            data = LocalDate.now(),
            title = "vou a praia",
            body = "de Joao Pessoa",
            idAuthor = UUID.randomUUID().toString()
        )

        //ação

        mockMvc.perform(MockMvcRequestBuilders
            .post(uri)
            .contentType(MediaType.APPLICATION_JSON)
            .content(toJson(request)))
            .andExpect(MockMvcResultMatchers.status().`is`(400))

        //assertivas
    }

    // 3 cenario de teste

    @Test
    fun `deve retornar 400, titulo vazio`() {

        // cenario

        val user = User(name = "Marina", email = "marina@gmail.com")
        userRepository.save(user)

        val uri = URI("/posts")

        val request = InsertPostRequest(
            data = LocalDate.now(),
            title = "",
            body = "de Joao Pessoa",
            idAuthor = user.id
        )

        //ação

        mockMvc.perform(MockMvcRequestBuilders
            .post(uri)
            .contentType(MediaType.APPLICATION_JSON)
            .content(toJson(request)))
            .andExpect(MockMvcResultMatchers.status().`is`(400))

        //assertivas
    }

    // 4 cenario de teste

    @Test
    fun `deve retornar 400, body vazio`() {

        // cenario

        val user = User(name = "Marina", email = "marina@gmail.com")
        userRepository.save(user)

        val uri = URI("/posts")

        val request = InsertPostRequest(
            data = LocalDate.now(),
            title = "vou a praia",
            body = "",
            idAuthor = user.id
        )

        //ação

        mockMvc.perform(MockMvcRequestBuilders
            .post(uri)
            .contentType(MediaType.APPLICATION_JSON)
            .content(toJson(request)))
            .andExpect(MockMvcResultMatchers.status().`is`(400))

        //assertivas
    }

    // 5 cenario de teste

    @Test
    fun `deve retornar 400, id author vazio`() {

        // cenario

        val user = User(name = "Marina", email = "marina@gmail.com")
        userRepository.save(user)

        val uri = URI("/posts")

        val request = InsertPostRequest(
            data = LocalDate.now(),
            title = "vou a praia",
            body = "de Joao Pessoa",
            idAuthor = ""
        )

        //ação

        mockMvc.perform(MockMvcRequestBuilders
            .post(uri)
            .contentType(MediaType.APPLICATION_JSON)
            .content(toJson(request)))
            .andExpect(MockMvcResultMatchers.status().`is`(400))

        //assertivas
    }

    // metodo para desserializar objetos da request

    fun toJson(request: InsertPostRequest): String {
        return objectMapper.writeValueAsString(request)
    }

}