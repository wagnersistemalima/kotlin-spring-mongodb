package br.com.wagner.workshopmongo.post.service

import br.com.wagner.workshopmongo.exceptions.GenericValidationException
import br.com.wagner.workshopmongo.post.repository.PostRepository
import br.com.wagner.workshopmongo.post.request.InsertPostRequest
import br.com.wagner.workshopmongo.user.repository.UserRepository
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class InsertPostService(
    @field:Autowired val postRepository: PostRepository,
    @field:Autowired val userRepository: UserRepository

    ) {

    val logger = LoggerFactory.getLogger(InsertPostService::class.java)

    // metodo contendo a logica para salvar um post de um usuario

    @Transactional
    fun insert(request: InsertPostRequest) {
        logger.info("------Execultando cadastro do post ${request.title} ----------")

        val possivelAuthor = userRepository.findById(request.idAuthor)

        // validação

        if(possivelAuthor.isEmpty) {
            logger.error("------Entrou no if, id author invalido------")
            throw GenericValidationException("Id author invalido")
        }

        val author = possivelAuthor.get()

        val post = request.toModel(author)

        postRepository.save(post)
        logger.info("--------Post salvo com sucesso ${request.title}  -------------")
    }
}