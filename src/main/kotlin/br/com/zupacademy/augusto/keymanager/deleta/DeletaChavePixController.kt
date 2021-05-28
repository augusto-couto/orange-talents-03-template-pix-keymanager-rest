package br.com.zupacademy.augusto.keymanager.deleta

import br.com.zupacademy.augusto.DeletePixRequest
import br.com.zupacademy.augusto.KeymanagerDeletaGrpcServiceGrpc
import io.micronaut.http.HttpResponse
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Delete
import org.slf4j.LoggerFactory
import java.util.*

@Controller
class DeletaChavePixController(
    private val deletaGrpcServiceGrpc: KeymanagerDeletaGrpcServiceGrpc.KeymanagerDeletaGrpcServiceBlockingStub
) {

    private val LOGGER = LoggerFactory.getLogger(this::class.java)

    @Delete("/api/v1/clientes/{clienteId}/pix/{pixId}")
    fun deleta(clienteId: UUID, pixId: Long): HttpResponse<Any> {

        LOGGER.info("$clienteId deleta o pix $pixId")

        deletaGrpcServiceGrpc.deleta(
            DeletePixRequest
                .newBuilder()
                .setIdCliente(clienteId.toString())
                .setIdPix(pixId)
                .build())
        return HttpResponse.ok()
    }

}