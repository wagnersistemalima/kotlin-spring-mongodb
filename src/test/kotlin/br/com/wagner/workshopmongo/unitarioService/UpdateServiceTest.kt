package br.com.wagner.workshopmongo.unitarioService

import br.com.wagner.workshopmongo.exceptions.GenericValidationException
import br.com.wagner.workshopmongo.exceptions.ResourceNotFoundException
import br.com.wagner.workshopmongo.user.model.User
import br.com.wagner.workshopmongo.user.repository.UserRepository
import br.com.wagner.workshopmongo.user.request.UpdateUserRequest
import br.com.wagner.workshopmongo.user.service.UpdateUserService
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito
import org.springframework.test.context.junit.jupiter.SpringExtension
import java.util.*

@ExtendWith(SpringExtension::class)
class UpdateServiceTest {

    @field:InjectMocks
    lateinit var updateUserService: UpdateUserService

    @field:Mock
    lateinit var userRepository: UserRepository

    // 1 cenario de teste / caminho feliz

    @Test
    fun `não deve lançar exception, retornar um objeto de resposta para o controller`() {

        // cenario

        val user = User(id = UUID.randomUUID().toString(), name = "Marina", email = "marina@zup.com.br")

        val request = UpdateUserRequest(email = "marina@gmail.com")

        // ação

        Mockito.`when`(userRepository.findById(user.id)).thenReturn(Optional.of(user))

        //assertiva

        Assertions.assertDoesNotThrow { updateUserService.update(user.id, request) }
    }

    // 2 cenario de teste

    @Test
    fun `deve lançar exception ResourceNotfoundException, quando id do usuario para atualizar não existe`() {

        // cenario

        val idInexistente = UUID.randomUUID().toString()

        val request = UpdateUserRequest(email = "marina@gmail.com")

        // ação

        Mockito.`when`(userRepository.findById(idInexistente)).thenReturn(Optional.ofNullable(null))

        //assertiva

        Assertions.assertThrows(ResourceNotFoundException::class.java) {updateUserService.update(idInexistente, request)}
    }

}