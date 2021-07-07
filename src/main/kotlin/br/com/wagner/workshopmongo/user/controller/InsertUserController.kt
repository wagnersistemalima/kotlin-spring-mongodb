package br.com.wagner.workshopmongo.user.controller

import br.com.wagner.workshopmongo.user.request.InsertUserRequest
import br.com.wagner.workshopmongo.user.service.InsertUserService
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.servlet.support.ServletUriComponentsBuilder
import javax.validation.Valid

@RestController
@RequestMapping("/users")
class InsertUserController(@field:Autowired val insertUserService: InsertUserService) {

    val logger = LoggerFactory.getLogger(InsertUserController::class.java)

    // end point inserir um usuario

    @PostMapping
    fun insert(@Valid @RequestBody request: InsertUserRequest): ResponseEntity<Any> {
        logger.info("-----Iniciando cadastro de um novo usuario ${request.name} -------")

        val response = insertUserService.insert(request)

        val uri = ServletUriComponentsBuilder
            .fromCurrentRequest()
            .path("/{id}")
            .buildAndExpand(response.id).toUri()

        logger.info("-----Usuario cadastrado com sucesso ${request.name} -------")
        return ResponseEntity.created(uri).body(response)
    }
}