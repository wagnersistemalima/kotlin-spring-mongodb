package br.com.wagner.workshopmongo.user.request

import javax.validation.constraints.Email
import javax.validation.constraints.NotBlank

data class UpdateUserRequest(

    @field:NotBlank
    @field:Email
    val email: String
){

}
