package br.com.wagner.workshopmongo.userTest.integracaoTest

import br.com.wagner.workshopmongo.user.model.User
import br.com.wagner.workshopmongo.user.repository.UserRepository
import br.com.wagner.workshopmongo.user.request.UpdateUserRequest
import br.com.wagner.workshopmongo.user.service.UpdateUserService
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
import java.util.*

@SpringBootTest
@AutoConfigureDataMongo
@AutoConfigureMockMvc
@ActiveProfiles("test")
class UpdateUserControllerTest {

    @field:Autowired
    lateinit var updateUserService: UpdateUserService

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

    // 1 cenario de testes/ caminho feliz

    @Test
    fun `deve retornar 200, atualizar email do usuario com id existente`() {

        // cenario

        val user = User(name = "wagner", email = "wagner@gmail.com")
        userRepository.save(user)

        val uri = UriComponentsBuilder.fromUriString("/users/{id}").buildAndExpand(user.id).toUri()

        val request = UpdateUserRequest(email = "wagner@zup.com.br")

        // ação

        mockMvc.perform(MockMvcRequestBuilders
            .put(uri)
            .contentType(MediaType.APPLICATION_JSON).content(toJson(request)))
            .andExpect(MockMvcResultMatchers.status().`is`(200))

        // assertivas

    }

    // 2 cenario de testes

    @Test
    fun `deve retornar 400, ao tentar atualizar usuario enviando email vazio`() {

        // cenario

        val user = User(name = "wagner", email = "wagner@gmail.com")
        userRepository.save(user)

        val uri = UriComponentsBuilder.fromUriString("/users/{id}").buildAndExpand(user.id).toUri()

        val request = UpdateUserRequest(email = "")

        // ação

        mockMvc.perform(MockMvcRequestBuilders
            .put(uri)
            .contentType(MediaType.APPLICATION_JSON).content(toJson(request)))
            .andExpect(MockMvcResultMatchers.status().`is`(400))

        // assertivas

    }

    // 3 cenario de testes

    @Test
    fun `deve retornar 400, ao tentar atualizar usuario enviando email ja cadastrado`() {

        // cenario

        val user1 = User(name = "wagner", email = "wagner@gmail.com")
        userRepository.save(user1)

        val user2 = User(name = "marina", email = "marina@gmail.com")
        userRepository.save(user2)

        val uri = UriComponentsBuilder.fromUriString("/users/{id}").buildAndExpand(user1.id).toUri()

        val request = UpdateUserRequest(email = "marina@gmail.com")

        // ação

        mockMvc.perform(MockMvcRequestBuilders
            .put(uri)
            .contentType(MediaType.APPLICATION_JSON).content(toJson(request)))
            .andExpect(MockMvcResultMatchers.status().`is`(400))

        // assertivas

    }

    // 4 cenario de testes

    @Test
    fun `deve retornar 400, ao tentar atualizar usuario enviando email invalido`() {

        // cenario

        val user1 = User(name = "wagner", email = "wagner@gmail.com")
        userRepository.save(user1)


        val uri = UriComponentsBuilder.fromUriString("/users/{id}").buildAndExpand(user1.id).toUri()

        val request = UpdateUserRequest(email = "wagner.com")

        // ação

        mockMvc.perform(MockMvcRequestBuilders
            .put(uri)
            .contentType(MediaType.APPLICATION_JSON).content(toJson(request)))
            .andExpect(MockMvcResultMatchers.status().`is`(400))

        // assertivas

    }

    // 5 cenario de testes

    @Test
    fun `deve retornar 404, ao tentar atualizar usuario de id inexistente`() {

        // cenario

        val user1 = User(name = "wagner", email = "wagner@gmail.com")
        userRepository.save(user1)

        val idInexistente = UUID.randomUUID().toString()

        val uri = UriComponentsBuilder.fromUriString("/users/{id}").buildAndExpand(idInexistente).toUri()

        val request = UpdateUserRequest(email = "wagner@zup.com")

        // ação

        mockMvc.perform(MockMvcRequestBuilders
            .put(uri)
            .contentType(MediaType.APPLICATION_JSON).content(toJson(request)))
            .andExpect(MockMvcResultMatchers.status().`is`(404))

        // assertivas

    }

    // metodo para desserializar objeto request

    fun toJson(request: UpdateUserRequest): String {
        return objectMapper.writeValueAsString(request)
    }

}