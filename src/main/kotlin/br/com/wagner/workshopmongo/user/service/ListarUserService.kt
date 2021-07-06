package br.com.wagner.workshopmongo.user.service

import br.com.wagner.workshopmongo.user.response.ListaUserResponse
import br.com.wagner.workshopmongo.user.repository.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.stream.Collectors

@Service
class ListarUserService(@field:Autowired val userRepository: UserRepository) {

    // logica para buscar todos usuarios no banco mongoDB

    @Transactional
    fun findAll() : MutableList<ListaUserResponse>{

        val list = userRepository.findAll()
        val response = list.stream().map { user -> ListaUserResponse(user) }.collect(Collectors.toList())

        return response

    }
}