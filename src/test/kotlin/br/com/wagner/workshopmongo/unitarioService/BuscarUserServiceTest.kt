package br.com.wagner.workshopmongo.unitarioService

import br.com.wagner.workshopmongo.exceptions.ResourceNotFoundException
import br.com.wagner.workshopmongo.user.model.User
import br.com.wagner.workshopmongo.user.repository.UserRepository
import br.com.wagner.workshopmongo.user.service.BuscarUserService
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito
import org.springframework.test.context.junit.jupiter.SpringExtension
import java.util.*

@ExtendWith(SpringExtension::class)
class BuscarUserServiceTest {

    @field:InjectMocks
    lateinit var buscarUserService: BuscarUserService

    @field:Mock
    lateinit var userRepository: UserRepository

    // 1 cenario de teste / caminho feliz

    @Test
    fun `deve retornar um usuario`() {

        // cenario

        val user = Optional.of(User(id = UUID.randomUUID().toString(), name = "Wagner Lima", email = "wagner@gmail.com"))
        val idExistente = user.get().id

        // ação

        // quando chamar userRepository.findById() deve retornar um usuario
        Mockito.`when`(userRepository.findById(idExistente)).thenReturn(user)

        //assertivas

        // comportamento = nao deve lançar exception
        Assertions.assertDoesNotThrow { buscarUserService.findById(idExistente) }

        // comportamento = verifica se foi chamado userRepository.findById()
        Mockito.verify(userRepository, Mockito.times(1)).findById(idExistente)
    }

    // 2 cenario de teste

    @Test
    fun `deve retornar exception ResourceNotFoundException quando id não encontrado`() {

        // cenario

        val idNaoExistente = UUID.randomUUID().toString()

        // ação

        // comportamento = deve retornar vazio
        Mockito.`when`(userRepository.findById(idNaoExistente)).thenReturn(Optional.empty())

        //assertivas

        // deve lançar exception
        Assertions.assertThrows(ResourceNotFoundException::class.java) {buscarUserService.findById(idNaoExistente)}

        // verifica se foi chamado userRepository.findById()
        Mockito.verify(userRepository, Mockito.times(1)).findById(idNaoExistente)
    }
}