package br.com.zupacademy.augusto

import br.com.zupacademy.augusto.keymanager.shared.MyExceptionHandler
import io.grpc.Status
import io.micronaut.http.HttpRequest
import io.grpc.StatusRuntimeException
import io.micronaut.http.HttpStatus
import io.micronaut.http.hateoas.JsonError
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

internal class MyExceptionHandlerTest {

    val requestGenerica = HttpRequest.GET<Any>("/")

    @Test
    internal fun `deve retornar 404 quando status NOT_FOUND`() {
        val exception = MyExceptionHandler()
            .handle(requestGenerica,
                StatusRuntimeException(Status.NOT_FOUND.withDescription("nao encontrado")))

        with(exception) {
            assertEquals(HttpStatus.NOT_FOUND, exception.status)
            assertNotNull(exception.body())
            assertEquals("nao encontrado", (exception.body() as JsonError).message)
        }
    }

    @Test
    internal fun `deve retornar 422 quando status ALREADY_EXISTS`() {
        val exception = MyExceptionHandler()
            .handle(requestGenerica,
                StatusRuntimeException(Status.ALREADY_EXISTS.withDescription("ja existente")))

        with(exception) {
            assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, exception.status)
            assertNotNull(exception.body())
            assertEquals("ja existente", (exception.body() as JsonError).message)
        }
    }

    @Test
    internal fun `deve retornar 400 quando status INVALID_ARGUMENT`() {
        val exception = MyExceptionHandler()
            .handle(requestGenerica,
                StatusRuntimeException(Status.INVALID_ARGUMENT))

        with(exception) {
            assertEquals(HttpStatus.BAD_REQUEST, exception.status)
            assertNotNull(exception.body())
            assertEquals("Dados de requisição inválidos.", (exception.body() as JsonError).message)
        }
    }

    @Test
    internal fun `deve retornar 403 quando status PERMISSIO_DENIED`() {
        val exception = MyExceptionHandler()
            .handle(requestGenerica,
                StatusRuntimeException(Status.PERMISSION_DENIED.withDescription("permissao negada")))

        with(exception) {
            assertEquals(HttpStatus.FORBIDDEN, exception.status)
            assertNotNull(exception.body())
            assertEquals("permissao negada", (exception.body() as JsonError).message)
        }
    }

    @Test
    internal fun `deve retornar 412 quando status FAILED_PRECONDITION`() {
        val exception = MyExceptionHandler()
            .handle(requestGenerica,
                StatusRuntimeException(Status.FAILED_PRECONDITION.withDescription("pre-condicao falhou")))

        with(exception) {
            assertEquals(HttpStatus.PRECONDITION_FAILED, exception.status)
            assertNotNull(exception.body())
            assertEquals("pre-condicao falhou", (exception.body() as JsonError).message)
        }
    }

    @Test
    internal fun `deve retornar 500 quando qualquer outro erro`() {
        val exception = MyExceptionHandler()
            .handle(requestGenerica,
                StatusRuntimeException(Status.INTERNAL.withDescription("erro interno")))

        with(exception) {
            assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, exception.status)
            assertNotNull(exception.body())
            assertEquals("Erro na requisição: erro interno (INTERNAL)", (exception.body() as JsonError).message)
        }
    }
}