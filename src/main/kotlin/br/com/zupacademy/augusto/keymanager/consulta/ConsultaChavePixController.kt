package br.com.zupacademy.augusto.keymanager.consulta

import br.com.zupacademy.augusto.ConsultaPixRequest
import br.com.zupacademy.augusto.KeyManagerConsultaGrpcServiceGrpc
import io.micronaut.http.HttpResponse
import io.micronaut.http.annotation.Body
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get
import org.slf4j.LoggerFactory
import java.util.*

@Controller
class ConsultaChavePixController(
    private val colsultaChavePixClient: KeyManagerConsultaGrpcServiceGrpc.KeyManagerConsultaGrpcServiceBlockingStub
) {

    private val LOGGER = LoggerFactory.getLogger(this::class.java)

    @Get("/api/v1/clientes/{clienteId}/pix/{pixId}")
    fun consulta(clienteId: UUID, pixId: Long): HttpResponse<Any> {

        LOGGER.info("$clienteId consultando pix $pixId")
        val response = colsultaChavePixClient.consulta(ConsultaPixRequest.newBuilder().setTipoBusca(
            ConsultaPixRequest.TipoBuscaPixId
                .newBuilder()
                .setClienteId(clienteId.toString())
                .setPixId(pixId)
                .build())
            .build()
        )
        return HttpResponse.ok(ConsultaChavePixResponse(response))
    }

}