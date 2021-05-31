package br.com.zupacademy.augusto.keymanager.lista

import br.com.zupacademy.augusto.KeyManagerListaChavesGrpcServiceGrpc
import br.com.zupacademy.augusto.ListaPixRequest
import br.com.zupacademy.augusto.keymanager.consulta.ConsultaChavePixResponse
import io.micronaut.http.HttpResponse
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get
import org.slf4j.LoggerFactory
import java.util.*

@Controller
class ListaChavePixController(
    private val listaChavesClient: KeyManagerListaChavesGrpcServiceGrpc.KeyManagerListaChavesGrpcServiceBlockingStub
) {

    private val LOGGER = LoggerFactory.getLogger(this::class.java)

    @Get("/api/v1/clientes/{clienteId}/pix/")
    fun lista(clienteId: UUID): HttpResponse<Any> {


        LOGGER.info("$clienteId listando chaves pix")
        val response = listaChavesClient.lista(ListaPixRequest
            .newBuilder()
            .setClienteId(clienteId.toString())
            .build())

        return HttpResponse.ok(ListaChavePixResponse(response))
    }

}