package br.com.wagner.workshopmongo.exceptions

import java.lang.RuntimeException

class ResourceNotFoundException(val msg: String): RuntimeException(msg) {
}