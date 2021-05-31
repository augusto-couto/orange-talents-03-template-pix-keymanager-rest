package br.com.zupacademy.augusto.keymanager.lista

import br.com.zupacademy.augusto.ListaPixResponse
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneOffset

class ListaChavePixResponse(pix: ListaPixResponse.Chave) {
    val pixId = pix.pixId
    val tipoChave = pix.tipoChave
    val chave = pix.chave
    val tipoConta = pix.tipoConta
    val  criadoEm = pix.criadoEm.let { timestamp ->
        LocalDateTime.ofInstant(
            Instant.ofEpochSecond(
                timestamp.seconds,
                timestamp.nanos.toLong()
            ), ZoneOffset.UTC
        )
    }
}