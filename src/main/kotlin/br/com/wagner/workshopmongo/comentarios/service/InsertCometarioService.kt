package br.com.wagner.workshopmongo.comentarios.service

import br.com.wagner.workshopmongo.comentarios.repository.ComentarioRepository
import br.com.wagner.workshopmongo.comentarios.request.InsertComentarioRequest
import br.com.wagner.workshopmongo.comentarios.response.ComentarioResponse
import br.com.wagner.workshopmongo.exceptions.ResourceNotFoundException
import br.com.wagner.workshopmongo.post.repository.PostRepository
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class InsertCometarioService(
    @field:Autowired val cometarioRepository: ComentarioRepository,
    @field:Autowired val postRepository: PostRepository
    ) {

    val logger = LoggerFactory.getLogger(InsertCometarioService::class.java)

    // metodo contendo a logica para inserir comentario em um post

    @Transactional
    fun insert(request: InsertComentarioRequest) {
        logger.info("--------Execultando cadastro de um comentario do post---------")

        // validação

        val post = postRepository.findById(request.idPost).orElseThrow { throw ResourceNotFoundException("id post nao encontrado") }

        val comentario = request.toModel(post)
        cometarioRepository.save(comentario)

        logger.info("--------Comentario salvo com sucesso---------- ")

    }
}