package br.com.wagner.workshopmongo.user.controller

import br.com.wagner.workshopmongo.user.service.DeleteUserService
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/users")
class DeleteUserController(@field:Autowired val deleteUserService: DeleteUserService) {

    val logger = LoggerFactory.getLogger(DeleteUserController::class.java)

    // end point para deletar um usuario por id

    @DeleteMapping("/{id}")
    fun delete(@PathVariable("id") id: String): ResponseEntity<Any> {
        logger.info("----------Iniciando a exclusão de um usuario------------")

        deleteUserService.delete(id)

        return ResponseEntity.noContent().build()
    }
}