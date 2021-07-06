package br.com.wagner.workshopmongo.integracaoTest

import br.com.wagner.workshopmongo.user.model.User
import br.com.wagner.workshopmongo.user.repository.UserRepository
import br.com.wagner.workshopmongo.user.response.ListaUserResponse
import br.com.wagner.workshopmongo.user.service.ListarUserService
import com.fasterxml.jackson.databind.ObjectMapper
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.data.mongo.AutoConfigureDataMongo
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import java.net.URI
import java.util.stream.Collectors

@SpringBootTest
@AutoConfigureDataMongo
@AutoConfigureMockMvc
class ListarUserControllerTest {

    @field:Autowired
    lateinit var userRepository: UserRepository

    @field:Autowired
    lateinit var mockMvc: MockMvc

    @field:Autowired
    lateinit var objectMapper: ObjectMapper

    @field:Autowired
    lateinit var userService: ListarUserService

    // rodar antes de cada teste

    @BeforeEach
    internal fun setUp() {
        userRepository.deleteAll()
    }


    @Test
    fun `deve retornar 200, com uma lista de usuarios`() {

        // cenario

        val user1 = User(name = "Wagner Lima", email = "wagner@email.com")
        userRepository.save(user1)

        val user2 = User(name = "Marina Lima", email = "marina@gmail.com")
        userRepository.save(user2)

        val uri = URI("/users")

        // ação

        val list = userRepository.findAll()
        val response = list.stream().map { user -> ListaUserResponse(user) }.collect(Collectors.toList())

        mockMvc.perform(MockMvcRequestBuilders.get(uri).contentType(MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers.status().`is`(200))
            .andExpect(MockMvcResultMatchers.content().json(toJson(response)))

        // assertivas
    }

    // metodo para desserializae objeto de resposta

    fun toJson(response: MutableList<ListaUserResponse>): String {
        return objectMapper.writeValueAsString(response)
    }
}