package br.com.wagner.workshopmongo.post.controller

import br.com.wagner.workshopmongo.post.request.InsertPostRequest
import br.com.wagner.workshopmongo.post.service.InsertPostService
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import javax.validation.Valid

@Validated
@RestController
@RequestMapping("/posts")
class InsertPostController(@field:Autowired val insertPostService: InsertPostService) {

    val logger = LoggerFactory.getLogger(InsertPostController::class.java)

    // end point insert post

    @PostMapping
    fun insert(@Valid @RequestBody request: InsertPostRequest): ResponseEntity<Any> {
        logger.info("------Iniciando cadastro de um post------")

        insertPostService.insert(request)

        return ResponseEntity.ok().build()
    }
}