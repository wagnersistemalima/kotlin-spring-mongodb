package br.com.wagner.workshopmongo.post.controller

import br.com.wagner.workshopmongo.post.service.BuscarPostIdService
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/posts")
class BuscarPostIdController(@field:Autowired val buscarPostIdService: BuscarPostIdService) {

    val logger = LoggerFactory.getLogger(BuscarPostIdController::class.java)

    // end point para buscar posts por id

    @GetMapping("/{id}")
    fun findById(@PathVariable("id") id: String): ResponseEntity<Any> {
        logger.info("-----Buscando post por id $id ----------")

        val response = buscarPostIdService.findById(id)

        logger.info("-----Busca realizada com sucesso----------")
        return ResponseEntity.ok().body(response)
    }


}