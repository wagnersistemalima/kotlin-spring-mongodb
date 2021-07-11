package br.com.wagner.workshopmongo.comentarios.controller

import br.com.wagner.workshopmongo.comentarios.request.InsertComentarioRequest
import br.com.wagner.workshopmongo.comentarios.service.InsertCometarioService
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
class InsertComentarioController(@field:Autowired val insertComentarioService: InsertCometarioService) {

    val logger = LoggerFactory.getLogger(InsertComentarioController::class.java)

    // end point para inserir comentarios nos posts

    @PostMapping("/comentarios")
    fun insert(@Valid @RequestBody request: InsertComentarioRequest): ResponseEntity<Any> {

        logger.info("------Iniciando inserção de cometario ao posts ${request.idPost} ----------")

        insertComentarioService.insert(request)

        return ResponseEntity.ok().build()
    }
}