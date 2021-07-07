package br.com.wagner.workshopmongo.integracaoTest

import br.com.wagner.workshopmongo.user.model.User
import br.com.wagner.workshopmongo.user.repository.UserRepository
import br.com.wagner.workshopmongo.user.response.BuscarUserResponse
import br.com.wagner.workshopmongo.user.service.BuscarUserService
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
import org.springframework.web.util.UriComponentsBuilder
import java.util.*

@SpringBootTest
@AutoConfigureDataMongo
@AutoConfigureMockMvc
@ActiveProfiles("test")
class BuscarUserControllerTest {

    @field:Autowired
    lateinit var userRepository: UserRepository

    @field:Mock
    lateinit var buscarUserService: BuscarUserService

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

    // 1 cenario de testes/ caminho feliz

    @Test
    fun `deve retornar 200, com os dados do usuario pesquisado pelo id`() {

        // cenario

        val user = User(name = "Wagner", email = "wagner@gmail.com")
        userRepository.save(user)

        val idExistente = user.id

        val userResponse = userRepository.findById(idExistente)

        val response = BuscarUserResponse(userResponse.get())

        val uri = UriComponentsBuilder.fromUriString("/users/{id}").buildAndExpand(idExistente).toUri()

        // ação

        Mockito.`when`(buscarUserService.findById(idExistente)).thenReturn(userResponse.get())

        mockMvc.perform(MockMvcRequestBuilders.get(uri)
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers.status().`is`(200))
            .andExpect(MockMvcResultMatchers.content().json(tojson(response)))

        //assertivas
    }

    // 2 cenario de testes

    @Test
    fun `deve retornar 404, recurso não encontrado quando id buscado não existir`() {

        // cenario

        val user = User(name = "Wagner", email = "wagner@gmail.com")
        userRepository.save(user)

        val idNaoExiste = UUID.randomUUID().toString()

        val uri = UriComponentsBuilder.fromUriString("/users/{id}").buildAndExpand(idNaoExiste).toUri()

        // ação

        mockMvc.perform(MockMvcRequestBuilders.get(uri)
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers.status().`is`(404))


        //assertivas
    }

    // metodo para desserializar objeto de resposta

    fun tojson(response: BuscarUserResponse): String {
        return objectMapper.writeValueAsString(response)
    }

}