package br.com.wagner.workshopmongo.user.service

import br.com.wagner.workshopmongo.exceptions.GenericValidationException
import br.com.wagner.workshopmongo.user.repository.UserRepository
import br.com.wagner.workshopmongo.user.request.InsertUserRequest
import br.com.wagner.workshopmongo.user.response.InsertUserResponse
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class InsertUserService(@field:Autowired val userRepository: UserRepository) {

    val logger = LoggerFactory.getLogger(InsertUserService::class.java)

    // metodo contendo a logica de cadastrar um novo usuario

    @Transactional
    fun insert(request: InsertUserRequest): InsertUserResponse {
        logger.info("----Execultando a logica para cadastrar um novo usuario ${request.name} -----")

        // validaçao email unico

        val possivelUser = userRepository.findByEmail(request.email)

        if (possivelUser.isPresent) {
            logger.error("---Entrou no if, email unico ja cadastrado ${request.email} ----")
            throw GenericValidationException("Campo unico, email ja cadastrado")
        }

        val user = request.toModel()
        userRepository.save(user)

        return InsertUserResponse(user)
    }
}