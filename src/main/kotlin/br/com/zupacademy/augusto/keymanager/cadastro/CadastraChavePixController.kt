package br.com.zupacademy.augusto.keymanager.cadastro

import br.com.zupacademy.augusto.KeymanagerCadastraGrpcServiceGrpc
import io.micronaut.http.HttpResponse
import io.micronaut.http.annotation.Body
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Post
import io.micronaut.validation.Validated
import org.slf4j.LoggerFactory
import java.util.*
import javax.validation.Valid

@Controller
@Validated
class CadastraChavePixController(
    private val cadastraChavePixClient: KeymanagerCadastraGrpcServiceGrpc.KeymanagerCadastraGrpcServiceBlockingStub
) {

    private val LOGGER = LoggerFactory.getLogger(this::class.java)

    @Post("/api/v1/clientes/{clienteId}/pix")
    fun cadastra(clienteId: UUID, @Valid @Body request: NovaChavePixRequest): HttpResponse<Any> {


        LOGGER.info("$clienteId cadastra chave pix ($request).")

        val response = cadastraChavePixClient.cadastra(request.toGrpcModel(clienteId))
        val location = HttpResponse.uri("/api/v1/clientes/$clienteId/pix/${response.idPix}")
        return HttpResponse.created(location)
    }

}