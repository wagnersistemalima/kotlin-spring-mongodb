package br.com.wagner.workshopmongo.userTest.unitarioService

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
    fun `deve retornar um usuario para ser atualizado`() {

        // cenario

        val user = User(id = UUID.randomUUID().toString(), name = "Marina", email = "marina@zup.com.br")

        val request = UpdateUserRequest(email = "marina@gmail.com")

        // ação

        // comportamento = deve retornar um usuario para ser atualizado
        Mockito.`when`(userRepository.findById(user.id)).thenReturn(Optional.of(user))

        // comportamento = deve retornar o usuario ao salvar as modificaçoes
        Mockito.`when`(userRepository.save(user)).thenReturn(user)

        //assertiva

        // nao deve lançar exception
        Assertions.assertDoesNotThrow { updateUserService.update(user.id, request) }

        // verifica se foi chamado o save
        Mockito.verify(userRepository, Mockito.times(1)).save(user)
    }

    // 2 cenario de teste

    @Test
    fun `deve lançar exception ResourceNotfoundException, quando id do usuario para atualizar não existe`() {

        // cenario

        val user = User(id = UUID.randomUUID().toString(), name = "Marina", email = "marina@zup.com.br")

        val idInexistente = UUID.randomUUID().toString()

        val request = UpdateUserRequest(email = "marina@gmail.com")

        // ação

        // comoportamento = deve retornar vazio
        Mockito.`when`(userRepository.findById(idInexistente)).thenReturn(Optional.empty())

        //assertiva

        // deve lançar exceptions
        Assertions.assertThrows(ResourceNotFoundException::class.java) {updateUserService.update(idInexistente, request)}

        // verifica se foi chamado o save
        Mockito.verify(userRepository, Mockito.times(0)).save(user)
    }

}