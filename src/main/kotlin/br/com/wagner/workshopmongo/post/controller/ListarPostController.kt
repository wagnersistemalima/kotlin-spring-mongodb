package br.com.wagner.workshopmongo.post.controller

import br.com.wagner.workshopmongo.post.service.ListarPostService
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/posts/author")
class ListarPostController(@field:Autowired val buscarPostService: ListarPostService) {

    val logger = LoggerFactory.getLogger(ListarPostController::class.java)

    // end point para listar todos os post por id author

    @GetMapping("/{id}")
    fun findById(@PathVariable("id") id: String): ResponseEntity<Any> {
        logger.info("------Iniciando a busca pelo poster do author $id --------")

        val response = buscarPostService.findById(id)

        return ResponseEntity.ok().body(response)
    }
}