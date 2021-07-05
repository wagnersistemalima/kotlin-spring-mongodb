package br.com.wagner.workshopmongo.user.repository

import br.com.wagner.workshopmongo.user.model.User
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository

@Repository
interface UserRepository: MongoRepository<User, String> {
}