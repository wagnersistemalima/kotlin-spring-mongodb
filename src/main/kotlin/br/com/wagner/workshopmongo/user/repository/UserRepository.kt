package br.com.wagner.workshopmongo.user.repository

import br.com.wagner.workshopmongo.user.model.User
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface UserRepository: MongoRepository<User, String> {

    abstract fun findByEmail(email: String): Optional<User>


}