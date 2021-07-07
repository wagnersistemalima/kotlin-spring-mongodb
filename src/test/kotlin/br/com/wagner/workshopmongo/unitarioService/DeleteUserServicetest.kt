package br.com.wagner.workshopmongo.unitarioService

import br.com.wagner.workshopmongo.exceptions.ResourceNotFoundException
import br.com.wagner.workshopmongo.user.model.User
import br.com.wagner.workshopmongo.user.repository.UserRepository
import br.com.wagner.workshopmongo.user.service.DeleteUserService
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito
import org.springframework.test.context.junit.jupiter.SpringExtension
import java.util.*

@ExtendWith(SpringExtension::class)
class DeleteUserServicetest {

    @field:InjectMocks
    lateinit var deleteUserService: DeleteUserService

    @field:Mock
    lateinit var userRepository: UserRepository

    // 1 cenario de testes/ caminho feliz

    @Test
    fun `não deve lançar exception ResourceNotfoundException, deve deletar um usuario `() {

        // cenario

        val user = User(name = "Maria", email = "maria@gmail.com")

        // ação

        Mockito.`when`(userRepository.findById(user.id)).thenReturn(Optional.of(user))

        // assertivas

        Assertions.assertDoesNotThrow { deleteUserService.delete(user.id) }
    }

    // 2 cenario de testes

    @Test
    fun `deve lançar exception ResourceNotfoundException, não deve deletar usuario `() {

        // cenario

        val user = User(name = "Maria", email = "maria@gmail.com")

        val idInexistente = UUID.randomUUID().toString()

        // ação

        Mockito.`when`(userRepository.findById(idInexistente)).thenReturn(Optional.ofNullable(null))

        // assertivas

        Assertions.assertThrows(ResourceNotFoundException::class.java) {deleteUserService.delete(idInexistente)}
    }
}