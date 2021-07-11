package br.com.wagner.workshopmongo.post.service

import br.com.wagner.workshopmongo.post.repository.PostRepository
import br.com.wagner.workshopmongo.post.response.BuscarPostResponse
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.stream.Collectors

@Service
class ListarPostService(@field:Autowired val postRepository: PostRepository) {

    val logger = LoggerFactory.getLogger(ListarPostService::class.java)

    // metodo contendo a logica para buscar posters de autores

    @Transactional
    fun findById(id: String): MutableList<BuscarPostResponse> {

        logger.info("-------Execultando a busca de post pelo id do autor $id -------------")

        val list = postRepository.findByAuthorId(id)

        val buscarPostResponse = list.stream().map { post -> BuscarPostResponse(post) }.collect(Collectors.toList())


        logger.info("--------------Retornando os posters do author id $id ---------------")
        return buscarPostResponse
    }
}