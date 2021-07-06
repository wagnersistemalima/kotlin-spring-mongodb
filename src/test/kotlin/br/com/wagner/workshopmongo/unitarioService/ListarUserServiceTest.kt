package br.com.wagner.workshopmongo.unitarioService

import br.com.wagner.workshopmongo.user.model.User
import br.com.wagner.workshopmongo.user.repository.UserRepository
import br.com.wagner.workshopmongo.user.response.ListaUserResponse
import br.com.wagner.workshopmongo.user.service.ListarUserService
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito
import org.springframework.test.context.junit.jupiter.SpringExtension
import java.util.stream.Collectors

@ExtendWith(SpringExtension::class)
class ListarUserServiceTest {

    @field:InjectMocks
    lateinit var userService: ListarUserService

    @field:Mock
    lateinit var userRepository: UserRepository

    // 1 cenario de teste

    @Test
    fun `deve retornar uma lista de objeto resposta DTO de usuarios`() {

        // cenario

        val user1 = User(name = "Wagner Lima", email = "wagner@gmail.com")
        val user2 = User(name = "Marina Lima", email = "marina@gmail.com")

        val list = mutableListOf<User>()

        list.add(user1)
        list.add(user2)

        val response = list.stream().map { user -> ListaUserResponse(user) }.collect(Collectors.toList())

        // ação

        Mockito.`when`(userRepository.findAll()).thenReturn(list)

        // assertivas

        Assertions.assertEquals(response, userService.findAll())
    }
}