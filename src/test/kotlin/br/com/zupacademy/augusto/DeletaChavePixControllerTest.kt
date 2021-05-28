package br.com.zupacademy.augusto

import br.com.zupacademy.augusto.keymanager.cadastro.NovaChavePixRequest
import br.com.zupacademy.augusto.keymanager.shared.grpc.KeyManagerGrpcFactory
import io.micronaut.context.annotation.Factory
import io.micronaut.context.annotation.Replaces
import io.micronaut.http.HttpRequest
import io.micronaut.http.HttpStatus
import io.micronaut.http.client.HttpClient
import io.micronaut.http.client.annotation.Client
import io.micronaut.test.extensions.junit5.annotation.MicronautTest
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.mockito.BDDMockito.given
import org.mockito.Mockito
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton

@MicronautTest
internal class DeletaChavePixControllerTest(
    private val deletaStub: KeymanagerDeletaGrpcServiceGrpc.KeymanagerDeletaGrpcServiceBlockingStub
) {

    @field:Inject
    @field:Client("/")
    lateinit var client: HttpClient

    @Test
    internal fun `deve deletar chave pix existente`() {
        val clienteId = UUID.randomUUID()
        val pixId = 1L

        given(deletaStub.deleta(Mockito.any())).willReturn(
            DeletePixResponse.newBuilder()
                .setIdCliente(clienteId.toString())
                .setIdPix(pixId)
                .build()
        )

        val request = HttpRequest.DELETE<Any>("/api/v1/clientes/$clienteId/pix/$pixId")

        val response = client.toBlocking().exchange(request, Any::class.java)

        with(response) {
            assertEquals(HttpStatus.OK, status)
        }
    }

    @Factory
    @Replaces(factory = KeyManagerGrpcFactory::class)
    internal class MockitoDeleteStubFactory {
        @Singleton
        fun deleteStubMock() = Mockito.mock(
            KeymanagerDeletaGrpcServiceGrpc
                .KeymanagerDeletaGrpcServiceBlockingStub::class.java
        )
    }

}