package br.com.wagner.workshopmongo.post.repository

import br.com.wagner.workshopmongo.post.model.Post
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository

@Repository
interface PostRepository: MongoRepository<Post, String> {
}