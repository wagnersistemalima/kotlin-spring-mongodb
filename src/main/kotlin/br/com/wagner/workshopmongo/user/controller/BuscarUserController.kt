package br.com.wagner.workshopmongo.user.controller

import br.com.wagner.workshopmongo.user.response.BuscarUserResponse
import br.com.wagner.workshopmongo.user.service.BuscarUserService
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("users")
class BuscarUserController(@field:Autowired val buscarUserService: BuscarUserService) {

    val logger = LoggerFactory.getLogger(BuscarUserController::class.java)

    // end point para buscar usuario por id

    @GetMapping("/{id}")
    fun findById(@PathVariable("id") id: String): ResponseEntity<Any> {
        logger.info("Iniciando busca de usuario por id ${id}")

        val user = buscarUserService.findById(id)

        logger.info("busca por id concluida com sucesso")
        return  ResponseEntity.ok().body(BuscarUserResponse(user))
    }
}