package br.com.zupacademy.augusto.keymanager.consulta

import br.com.zupacademy.augusto.ConsultaPixResponse
import io.micronaut.core.annotation.Introspected
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneOffset

@Introspected
class ConsultaChavePixResponse(response: ConsultaPixResponse) {
    val clienteId = response.clienteId
    val pixId = response.pixId
    val chavePix = mapOf(
        Pair("tipoChave", response.chavePix.tipoChave.name),
        Pair("chave", response.chavePix.chave),
        Pair(
            "conta", mapOf(
                Pair("tipoConta", response.chavePix.conta.tipoConta.name),
                Pair("instituicao", response.chavePix.conta.instituicao),
                Pair("nomeTitular", response.chavePix.conta.nomeTitular),
                Pair("cpfTitular", response.chavePix.conta.cpfTitular),
                Pair("agencia", response.chavePix.conta.agencia),
                Pair("numeroConta", response.chavePix.conta.numeroConta)
            )
        ),
        Pair("criadaEm", response.chavePix.criadaEm.let {
            LocalDateTime.ofInstant(Instant.ofEpochSecond(it.seconds, it.nanos.toLong()), ZoneOffset.UTC)
        })
    )
}