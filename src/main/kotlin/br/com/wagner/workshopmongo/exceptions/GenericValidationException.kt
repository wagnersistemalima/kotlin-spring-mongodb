package br.com.wagner.workshopmongo.exceptions

import java.lang.RuntimeException

class GenericValidationException(val msg: String): RuntimeException(msg) {
}