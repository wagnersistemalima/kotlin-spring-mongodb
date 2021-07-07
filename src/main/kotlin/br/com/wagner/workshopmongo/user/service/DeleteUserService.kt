package br.com.wagner.workshopmongo.user.service

import br.com.wagner.workshopmongo.exceptions.ResourceNotFoundException
import br.com.wagner.workshopmongo.user.repository.UserRepository
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class DeleteUserService(@field:Autowired val userRepository: UserRepository) {

    val logger = LoggerFactory.getLogger(DeleteUserService::class.java)

    // metodo contendo a logica para deletar um usuario por id

    fun delete(id: String) {
        logger.info("----------Execultando a exlusao do usuario  $id ----------")

        val possivelUser = userRepository.findById(id)

        // validação

        if(possivelUser.isEmpty) {
            logger.error("-----Entrou no if, usuario não encontrado $id --------")
            throw ResourceNotFoundException("Objeto não encontrado")
        }

        val user = possivelUser.get()
        userRepository.delete(user)
        logger.info("Usuario excluido com sucesso ${user.id} -----")
    }
}