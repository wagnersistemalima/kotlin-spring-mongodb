package br.com.wagner.workshopmongo

import br.com.wagner.workshopmongo.user.model.User
import org.mockito.Mockito

object MockitoHelper {
    fun <T> anyObject(list: MutableList<User>): T {
        Mockito.any<T>()
        return uninitialized()
    }
    @Suppress("UNCHECKED_CAST")
    fun <T> uninitialized(): T =  null as T

    fun <T> eq(obj: T): T = Mockito.eq<T>(obj)
}