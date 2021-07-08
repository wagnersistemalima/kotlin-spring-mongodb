package br.com.wagner.workshopmongo.userTest.integracaoTest

import br.com.wagner.workshopmongo.user.model.User
import br.com.wagner.workshopmongo.user.repository.UserRepository
import br.com.wagner.workshopmongo.user.request.InsertUserRequest
import br.com.wagner.workshopmongo.user.service.InsertUserService
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

@SpringBootTest
@AutoConfigureDataMongo
@AutoConfigureMockMvc
@ActiveProfiles("test")
class InsertUserControllerTest {


    @field:Autowired
    lateinit var userRepository: UserRepository

    @field:Autowired
    lateinit var insertUserService: InsertUserService

    @field:Autowired
    lateinit var mockMvc: MockMvc

    @field:Autowired
    lateinit var objectMapper: ObjectMapper

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

    // 1 cenario de teste/ caminho feliz

    @Test
    fun `deve retornar 201 created, cadastrar um novo usuario`() {

        // cenario

        val request = InsertUserRequest(name = "Pedro", email = "pedro@gmail.com")

        val uri = URI("/users")

        // ação
        mockMvc.perform(MockMvcRequestBuilders.post(uri)
            .contentType(MediaType.APPLICATION_JSON).content(toJson(request)))
            .andExpect(MockMvcResultMatchers.status().`is`(201))

        // assertivas

    }

    // 2 cenario de teste/

    @Test
    fun `deve retornar 400 badRequest, ao tentar cadastrar um novo usuario com email invalido`() {

        // cenario

        val request = InsertUserRequest(name = "Pedro", email = "pedrogmail.com")

        val uri = URI("/users")

        // ação
        mockMvc.perform(MockMvcRequestBuilders.post(uri)
            .contentType(MediaType.APPLICATION_JSON).content(toJson(request)))
            .andExpect(MockMvcResultMatchers.status().`is`(400))

        // assertivas

    }

    // 3 cenario de teste/

    @Test
    fun `deve retornar 400 badRequest, ao tentar cadastrar um novo usuario com email vazio`() {

        // cenario

        val request = InsertUserRequest(name = "Pedro", email = "")

        val uri = URI("/users")

        // ação
        mockMvc.perform(MockMvcRequestBuilders.post(uri)
            .contentType(MediaType.APPLICATION_JSON).content(toJson(request)))
            .andExpect(MockMvcResultMatchers.status().`is`(400))

        // assertivas

    }

    // 4 cenario de teste/

    @Test
    fun `deve retornar 400 badRequest, ao tentar cadastrar um novo usuario com nome vazio`() {

        // cenario

        val request = InsertUserRequest(name = "", email = "pedro@gmail.com")

        val uri = URI("/users")

        // ação
        mockMvc.perform(MockMvcRequestBuilders.post(uri)
            .contentType(MediaType.APPLICATION_JSON).content(toJson(request)))
            .andExpect(MockMvcResultMatchers.status().`is`(400))

        // assertivas

    }

    // 5 cenario de teste/

    @Test
    fun `deve retornar 400 badRequest, ao tentar cadastrar um novo usuario com email já cadastrado`() {

        // cenario

        val request = InsertUserRequest(name = "Pedro", email = "karina@gmail.com")

        val user = User(name = "Karina Maciel", email = "karina@gmail.com")
        userRepository.save(user)

        val uri = URI("/users")

        // ação
        mockMvc.perform(MockMvcRequestBuilders.post(uri)
            .contentType(MediaType.APPLICATION_JSON).content(toJson(request)))
            .andExpect(MockMvcResultMatchers.status().`is`(400))

        // assertivas

    }

    // metodo para desserializar objeto da request

    fun toJson(request: InsertUserRequest): String {
        return  objectMapper.writeValueAsString(request)
    }


}