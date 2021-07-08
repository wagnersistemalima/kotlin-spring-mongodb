package br.com.wagner.workshopmongo.userTest.integracaoTest

import br.com.wagner.workshopmongo.user.model.User
import br.com.wagner.workshopmongo.user.repository.UserRepository
import br.com.wagner.workshopmongo.user.service.DeleteUserService
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
class DeleteUserControllerTest {

    @field:Autowired
    lateinit var userRepository: UserRepository

    @field:Autowired
    lateinit var deleteUserService: DeleteUserService

    @field:Autowired
    lateinit var mockMvc: MockMvc

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
    fun `deve retornar 204, deletar um usuario por id`() {

        // cenario

        val user = User(name = "Carla", email = "carla@gmail.com")
        userRepository.save(user)

        val uri = UriComponentsBuilder.fromUriString("/users/{id}").buildAndExpand(user.id).toUri()

        // ação

        mockMvc.perform(MockMvcRequestBuilders
            .delete(uri)
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers.status().`is`(204))

        //assertvivas
    }

    // 2 cenario de testes

    @Test
    fun `deve retornar 404, ao tentar deletar um usuario por id inexistente`() {

        // cenario

        val user = User(name = "Carla", email = "carla@gmail.com")
        userRepository.save(user)

        val idInexistente = UUID.randomUUID().toString()

        val uri = UriComponentsBuilder.fromUriString("/users/{id}").buildAndExpand(idInexistente).toUri()

        // ação

        mockMvc.perform(MockMvcRequestBuilders
            .delete(uri)
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers.status().`is`(404))

        //assertvivas
    }
}