package br.com.zupacademy.augusto.keymanager.lista

import br.com.zupacademy.augusto.ListaPixResponse
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneOffset

class ListaChavePixResponse(listaPixResponse: ListaPixResponse) {
    val clienteId = listaPixResponse.clienteId
    val chaves = listaPixResponse.chavesList.map {
        mapOf(
            Pair("pixId", it.pixId),
            Pair("tipoChave", it.tipoChave.name),
            Pair("chave", it.chave),
            Pair("tipoConta", it.tipoConta.name),
            Pair("tipoConta", it.tipoConta.name),
            Pair("criadaEm", it.criadoEm.let { timestamp ->
                LocalDateTime.ofInstant(
                    Instant.ofEpochSecond(
                        timestamp.seconds,
                        timestamp.nanos.toLong()
                    ), ZoneOffset.UTC
                )
            })
        )
    }
}