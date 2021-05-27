package br.com.zupacademy.augusto

import br.com.zupacademy.augusto.keymanager.cadastro.NovaChavePixRequest
import br.com.zupacademy.augusto.keymanager.cadastro.TipoChaveRequest
import br.com.zupacademy.augusto.keymanager.cadastro.TipoContaRequest
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
internal class CadastraChavePixControllerTest(
    private val cadastraStub: KeymanagerCadastraGrpcServiceGrpc.KeymanagerCadastraGrpcServiceBlockingStub
) {

    @field:Inject
    @field:Client("/")
    lateinit var client: HttpClient

    @Test
    internal fun `deve cadastrar uma chave pix`() {
        val pixId = 1L

        val responseGrpc = NewPixResponse.newBuilder()
            .setIdPix(pixId)
            .build()

        given(cadastraStub.cadastra(Mockito.any())).willReturn(responseGrpc)

        val novaChave = NovaChavePixRequest(
            TipoContaRequest.CONTA_CORRENTE,
            "",
            TipoChaveRequest.RANDOM
        )

        val clienteId = UUID.randomUUID()

        val request = HttpRequest.POST("/api/v1/clientes/$clienteId/pix", novaChave)
        val response = client.toBlocking().exchange(request, NovaChavePixRequest::class.java)

        with(response) {
            assertEquals(HttpStatus.CREATED, status)
            assertTrue(headers.contains("Location"))
            assertTrue(header("Location")!!.contains(pixId.toString()))
        }
    }

    @Factory
    @Replaces(factory = KeyManagerGrpcFactory::class)
    internal class MockitoStubFactory {
        @Singleton
        fun stubMock() = Mockito.mock(
            KeymanagerCadastraGrpcServiceGrpc
                .KeymanagerCadastraGrpcServiceBlockingStub::class.java
        )
    }

}