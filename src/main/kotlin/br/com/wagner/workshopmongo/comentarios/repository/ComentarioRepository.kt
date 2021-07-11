package br.com.wagner.workshopmongo.comentarios.repository

import br.com.wagner.workshopmongo.comentarios.model.Comentario
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository

@Repository
interface ComentarioRepository: MongoRepository<Comentario, String> {
}