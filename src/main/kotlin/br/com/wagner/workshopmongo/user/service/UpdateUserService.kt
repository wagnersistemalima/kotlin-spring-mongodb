package br.com.wagner.workshopmongo.user.service

import br.com.wagner.workshopmongo.exceptions.GenericValidationException
import br.com.wagner.workshopmongo.exceptions.ResourceNotFoundException
import br.com.wagner.workshopmongo.user.repository.UserRepository
import br.com.wagner.workshopmongo.user.request.UpdateUserRequest
import br.com.wagner.workshopmongo.user.response.BuscarUserResponse
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class UpdateUserService(@field:Autowired val userRepository: UserRepository) {

    val logger = LoggerFactory.getLogger(UpdateUserService::class.java)

    // metodo contendo a logica para atualizar um usuario

    @Transactional
    fun update(id: String, request: UpdateUserRequest): BuscarUserResponse {
        logger.info("------Execultando a atualização de um usuario $id -------")
        val possivelUser = userRepository.findById(id)
        val possivelEmail = userRepository.findByEmail(request.email)

        // validação se existe o usuario

        if(possivelUser.isEmpty) {
            logger.error("-----Entrou no if, recurso não encontrado $id ---------")
            throw ResourceNotFoundException("Recurso não encontrado $id")
        }

        // validação para campo email unico

        if(possivelEmail.isPresent) {
            logger.error("-------Entrou no if, email para atualização ja existe ${request.email} -------")
            throw GenericValidationException("Campo unico, email para atualização ja existe")
        }

        val user = possivelUser.get()
        user.email = request.email
        user.update()
        userRepository.save(user)

        logger.info("------Usuario atualizado com sucesso-----")
        return BuscarUserResponse(user)
    }
}