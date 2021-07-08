package br.com.wagner.workshopmongo.unitarioService

import br.com.wagner.workshopmongo.MockitoHelper
import br.com.wagner.workshopmongo.exceptions.GenericValidationException
import br.com.wagner.workshopmongo.user.model.User
import br.com.wagner.workshopmongo.user.repository.UserRepository
import br.com.wagner.workshopmongo.user.request.InsertUserRequest
import br.com.wagner.workshopmongo.user.response.InsertUserResponse
import br.com.wagner.workshopmongo.user.service.InsertUserService
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.*
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
    fun `deve salvar usuario, retornar objeto de resposta`() {

        // cenario

        val request = InsertUserRequest(name = "Marina", email = "marina@gmail.com")

        val user = request.toModel()

        val response = InsertUserResponse(user)

        // ação

        // comportamento = deve retornar vazio
        Mockito.`when`(userRepository.findByEmail(request.email)).thenReturn(Optional.empty())

        // comportamento = deve retornar usuario ao salvar
        Mockito.`when`(userRepository.save(user)).thenReturn(user)

        //assertivas

        // nao deve lançar exception
        Assertions.assertDoesNotThrow { insertUserService.insert(request) }
        Assertions.assertDoesNotThrow { userRepository.save(user) }

        // verifica se foi chamado o save
        Mockito.verify(userRepository, Mockito.times(1)).save(user)

        Assertions.assertEquals(response.id, user.id)


    }

    // 2 cenario de teste

    @Test
    fun `deve lançar exception quando tentar cadastrar usuario com email ja existente`() {

        // cenario

        val request = InsertUserRequest(name = "Marina", email = "karina@gmail.com")

        val user = User(name = "Karina", email = "karina@gmail.com")

        // ação

        // comportamento = deve retornar um usuario ao chamar userRepository.findByEmail()
        Mockito.`when`(userRepository.findByEmail(request.email)).thenReturn(Optional.of(user))

        //assertivas

        // deve lançar exceptions email unico
        Assertions.assertThrows(GenericValidationException::class.java) {insertUserService.insert(request)}

        // verifica se foi chamado o save
        Mockito.verify(userRepository, Mockito.times(0)).save(user)

    }
}