package br.com.wagner.workshopmongo.user.controller

import br.com.wagner.workshopmongo.user.service.ListarUserService
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("users")
class ListarUserController(@field:Autowired val userService: ListarUserService) {

    val logger = LoggerFactory.getLogger(ListarUserController::class.java)

    // end point buscar todos usuarios / get

    @GetMapping
    fun findAll(): ResponseEntity<Any> {
        logger.info("--------Iniciando a busca de todos usuarios----------")

        val response = userService.findAll()

        logger.info("----------busca realizada com sucesso-------------")
        return ResponseEntity.ok().body(response)
    }
}