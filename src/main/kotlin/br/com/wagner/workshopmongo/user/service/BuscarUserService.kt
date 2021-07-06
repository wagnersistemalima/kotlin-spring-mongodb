package br.com.wagner.workshopmongo.user.service

import br.com.wagner.workshopmongo.exceptions.ResourceNotFoundException
import br.com.wagner.workshopmongo.user.model.User
import br.com.wagner.workshopmongo.user.repository.UserRepository
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class BuscarUserService(@field:Autowired val userRepository: UserRepository) {

    val logger = LoggerFactory.getLogger(BuscarUserService::class.java)

    // metodo contendo a logica de buscar um usuario por id

    @Transactional
    fun findById(id: String): User {
        logger.info("---------Execultando a busca por id $id ----------")
        val possivelUser = userRepository.findById(id)

        if(possivelUser.isEmpty) {
            logger.error("---------Entrou no if, id do usuario não encontrado $id ----------")
            throw ResourceNotFoundException("id do usuario não encontrado")
        }

        val user = possivelUser.get()

        logger.info("--------retornando com o usuario buscado pelo id $id ---------")
        return  user
    }
}