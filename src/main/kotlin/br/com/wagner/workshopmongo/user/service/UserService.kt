package br.com.wagner.workshopmongo.user.service

import br.com.wagner.workshopmongo.user.response.UserResponse
import br.com.wagner.workshopmongo.user.repository.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.stream.Collectors

@Service
class UserService(@field:Autowired val userRepository: UserRepository) {

    // logica para buscar todos usuarios no banco mongoDB

    @Transactional
    fun findAll() : MutableList<UserResponse>{

        val list = userRepository.findAll()
        val response = list.stream().map { user -> UserResponse(user) }.collect(Collectors.toList())

        return response

    }
}