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
internal class ConsultaChavePixControllerTest(
    private val consultaStub: KeyManagerConsultaGrpcServiceGrpc.KeyManagerConsultaGrpcServiceBlockingStub
) {

    @field:Inject
    @field:Client("/")
    lateinit var client: HttpClient

    @Test
    internal fun `deve consultar uma chave pix existente`() {
        val clienteId = UUID.randomUUID()
        val pixId = 1L

        val consultaResponse = ConsultaPixResponse.newBuilder()
            .setClienteId(clienteId.toString())
            .setPixId(pixId.toString())
            .setChavePix(
                ChavePix.newBuilder()
                    .setTipoChave(TipoChave.valueOf(TipoChave.CPF.name))
                    .setChave("02228707553")
                    .setConta(
                        Conta.newBuilder()
                            .setTipoConta(TipoConta.valueOf(TipoConta.CONTA_CORRENTE.name))
                            .setInstituicao("60701190")
                            .setNomeTitular("Rafael M C Ponte")
                            .setCpfTitular("02467781054")
                            .setAgencia("0001")
                            .setNumeroConta("291900")
                    )
                    .setCriadaEm(LocalDateTime.now().let {
                        val createdAt = it.atZone(ZoneId.of("UTC")).toInstant()
                        Timestamp.newBuilder()
                            .setSeconds(createdAt.epochSecond)
                            .setNanos(createdAt.nano).build()
                    })
                    .build()
            )
            .build()

        given(
            consultaStub.consulta(
                ConsultaPixRequest.newBuilder().setTipoBusca(
                    ConsultaPixRequest.TipoBuscaPixId
                        .newBuilder()
                        .setClienteId(clienteId.toString())
                        .setPixId(pixId)
                        .build()
                )
                    .build()
            )
        ).willReturn(consultaResponse)

        val request = HttpRequest.GET<Any>("/api/v1/clientes/$clienteId/pix/$pixId")
        val response = client.toBlocking().exchange(request, Any::class.java)

        with(response) {
            assertEquals(HttpStatus.OK, status)
            assertNotNull(body())
        }
    }

    @Factory
    @Replaces(factory = KeyManagerGrpcFactory::class)
    internal class MockitoStubFactory {
        @Singleton
        fun consultaStubMock() = Mockito.mock(
            KeyManagerConsultaGrpcServiceGrpc
                .KeyManagerConsultaGrpcServiceBlockingStub::class.java
        )
    }
}