package br.com.wagner.workshopmongo.user.controller

import br.com.wagner.workshopmongo.user.request.UpdateUserRequest
import br.com.wagner.workshopmongo.user.service.UpdateUserService
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@Validated
@RestController
@RequestMapping("/users")
class UpdateUserController(@field:Autowired val updateUserService: UpdateUserService) {

    val logger = LoggerFactory.getLogger(UpdateUserController::class.java)

    // end point para atualizar dados de usuario

    @PutMapping("/{id}")
    fun update(@PathVariable("id") id: String,@Valid @RequestBody request: UpdateUserRequest): ResponseEntity<Any> {

        val response = updateUserService.update(id, request)

        return ResponseEntity.ok().body(response)
    }
}