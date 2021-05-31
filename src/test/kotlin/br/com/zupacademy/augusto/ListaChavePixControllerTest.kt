package br.com.zupacademy.augusto

import br.com.zupacademy.augusto.keymanager.shared.grpc.KeyManagerGrpcFactory
import com.google.protobuf.Timestamp
import io.micronaut.context.annotation.Factory
import io.micronaut.context.annotation.Replaces
import io.micronaut.http.HttpRequest
import io.micronaut.http.HttpStatus
import io.micronaut.http.client.HttpClient
import io.micronaut.http.client.annotation.Client
import io.micronaut.test.extensions.junit5.annotation.MicronautTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Test
import org.mockito.BDDMockito.given
import org.mockito.Mockito
import java.time.LocalDateTime
import java.time.ZoneId
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton

@MicronautTest
internal class ListaChavePixControllerTest(
    private val listaPixStub: KeyManagerListaChavesGrpcServiceGrpc.KeyManagerListaChavesGrpcServiceBlockingStub
) {

    @field:Inject
    @field:Client("/")
    lateinit var client: HttpClient

    @Test
    internal fun `deve listar as chaves de um cliente`() {
        val clienteId = UUID.randomUUID()
        val pixId = 1L
        val pix2Id = 2L

        val chave1 = ListaPixResponse.Chave
            .newBuilder()
            .setPixId(pixId.toString())
            .setTipoChave(TipoChave.CPF)
            .setChave("26856358075")
            .setTipoConta(TipoConta.CONTA_CORRENTE)
            .setCriadoEm(LocalDateTime.now().let {
                val createdAt = it.atZone(ZoneId.of("UTC")).toInstant()
                Timestamp.newBuilder()
                    .setSeconds(createdAt.epochSecond)
                    .setNanos(createdAt.nano).build()
            })
            .build()

        val chave2 = ListaPixResponse.Chave
            .newBuilder()
            .setPixId(pix2Id.toString())
            .setTipoChave(TipoChave.RANDOM)
            .setChave("")
            .setTipoConta(TipoConta.CONTA_CORRENTE)
            .setCriadoEm(LocalDateTime.now().let {
                val createdAt = it.atZone(ZoneId.of("UTC")).toInstant()
                Timestamp.newBuilder()
                    .setSeconds(createdAt.epochSecond)
                    .setNanos(createdAt.nano).build()
            })
            .build()

        val listaResponse = ListaPixResponse
            .newBuilder()
            .setClienteId(clienteId.toString())
            .addAllChaves(listOf(chave1, chave2))
            .build()


        given(
            listaPixStub.lista(
                ListaPixRequest
                    .newBuilder()
                    .setClienteId(clienteId.toString())
                    .build()
            )
        ).willReturn(listaResponse)



        val request = HttpRequest.GET<Any>("/api/v1/clientes/$clienteId/pix/")
        val response = client.toBlocking().exchange(request, List::class.java)

        with(response) {
            assertEquals(HttpStatus.OK, status)
            assertNotNull(body())
            assertEquals(2, response.body().size)
        }
    }

    @Factory
    @Replaces(factory = KeyManagerGrpcFactory::class)
    internal class MockitoStubFactory {
        @Singleton
        fun listaStubMock() = Mockito.mock(
            KeyManagerListaChavesGrpcServiceGrpc
                .KeyManagerListaChavesGrpcServiceBlockingStub::class.java
        )
    }
}