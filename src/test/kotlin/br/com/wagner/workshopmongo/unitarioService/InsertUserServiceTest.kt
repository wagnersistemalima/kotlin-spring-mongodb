package br.com.wagner.workshopmongo.unitarioService

import br.com.wagner.workshopmongo.exceptions.GenericValidationException
import br.com.wagner.workshopmongo.user.model.User
import br.com.wagner.workshopmongo.user.repository.UserRepository
import br.com.wagner.workshopmongo.user.request.InsertUserRequest
import br.com.wagner.workshopmongo.user.response.InsertUserResponse
import br.com.wagner.workshopmongo.user.service.InsertUserService
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito
import org.springframework.test.context.junit.jupiter.SpringExtension
import java.util.*

@ExtendWith(SpringExtension::class)
class InsertUserServiceTest {

    @field:InjectMocks
    lateinit var insertUserService: InsertUserService

    @field:Mock
    lateinit var userRepository: UserRepository


    // 1 cenario de teste/ caminho feliz

    @Test
    fun `deve converter request em entidade e devolver objeto de resposta sem lançar exception`() {

        // cenario

        val request = InsertUserRequest(name = "Marina", email = "marina@gmail.com")

        val user = request.toModel()

        val response = InsertUserResponse(user)

        // ação

        Mockito.`when`(userRepository.findByEmail(request.email)).thenReturn(Optional.ofNullable(null))

        //assertivas

        Assertions.assertDoesNotThrow { insertUserService.insert(request) }
        Assertions.assertEquals(user.id, response.id)


    }

    // 2 cenario de teste

    @Test
    fun `deve lançar exception quando tentar cadastrar usuario com email ja existente`() {

        // cenario

        val request = InsertUserRequest(name = "Marina", email = "karina@gmail.com")

        val user = User(name = "Karina", email = "karina@gmail.com")

        // ação

        Mockito.`when`(userRepository.findByEmail(request.email)).thenReturn(Optional.of(user))

        //assertivas

        Assertions.assertThrows(GenericValidationException::class.java) {insertUserService.insert(request)}

    }
}