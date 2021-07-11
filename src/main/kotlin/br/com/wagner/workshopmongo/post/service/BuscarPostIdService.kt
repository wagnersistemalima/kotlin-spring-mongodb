package br.com.wagner.workshopmongo.post.service

import br.com.wagner.workshopmongo.exceptions.ResourceNotFoundException
import br.com.wagner.workshopmongo.post.repository.PostRepository
import br.com.wagner.workshopmongo.post.response.BuscarPostResponse
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class BuscarPostIdService(@field:Autowired val postRepository: PostRepository) {

    val logger = LoggerFactory.getLogger(BuscarPostIdService::class.java)

    // end point contendo a logica para buscar um post por id

    @Transactional
    fun findById(id: String): BuscarPostResponse {
        logger.info("--------Execultando a busca de post por id $id ------------")

        val post = postRepository.findById(id).orElseThrow { ResourceNotFoundException("Id do post não encontrado") }

        logger.info("--------Retornando a busca do post pelo id: $id ----------")
        return BuscarPostResponse(post)

    }
}