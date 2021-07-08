package br.com.wagner.workshopmongo.userTest.unitarioService

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

        val idExiste = UUID.randomUUID().toString()

        // ação

        // retorna um usuario ao chamar userRepository.findById()
        Mockito.`when`(userRepository.findById(idExiste)).thenReturn(Optional.of(user))

        // nao faça nada quando chamar userRepository.delete()
        Mockito.doNothing().`when`(userRepository).delete(user)

        // assertivas

        Assertions.assertDoesNotThrow { deleteUserService.delete(idExiste) } //-> nao deve lançar exception

        // verifica se foi chamado userRepository.delete()
        Mockito.verify(userRepository, Mockito.times(1)).delete(user)
    }

    // 2 cenario de testes

    @Test
    fun `deve lançar exception ResourceNotfoundException, não deve deletar usuario `() {

        // cenario

        val user = User(name = "Maria", email = "maria@gmail.com")

        val idInexistente = UUID.randomUUID().toString()

        // ação

        // retorna vazio quando chamar findById() com id que não existe
        Mockito.`when`(userRepository.findById(idInexistente)).thenReturn(Optional.empty())

        // assertivas

        // deve lançar exception
        Assertions.assertThrows(ResourceNotFoundException::class.java) {deleteUserService.delete(idInexistente)}

        // verifica se foi chamado userRepository.delete()
        Mockito.verify(userRepository, Mockito.times(0)).delete(user)
    }
}