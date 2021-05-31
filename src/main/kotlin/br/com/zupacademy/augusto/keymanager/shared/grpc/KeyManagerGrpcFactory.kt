package br.com.zupacademy.augusto.keymanager.shared.grpc

import br.com.zupacademy.augusto.KeyManagerConsultaGrpcServiceGrpc
import br.com.zupacademy.augusto.KeyManagerListaChavesGrpcServiceGrpc
import br.com.zupacademy.augusto.KeymanagerCadastraGrpcServiceGrpc
import br.com.zupacademy.augusto.KeymanagerDeletaGrpcServiceGrpc
import io.grpc.ManagedChannel
import io.micronaut.context.annotation.Factory
import io.micronaut.grpc.annotation.GrpcChannel
import javax.inject.Singleton

@Factory
class KeyManagerGrpcFactory(@GrpcChannel("keyManager") val channel: ManagedChannel) {

    @Singleton
    fun cadastraChave() = KeymanagerCadastraGrpcServiceGrpc.newBlockingStub(channel)

    @Singleton
    fun deletaChave() = KeymanagerDeletaGrpcServiceGrpc.newBlockingStub(channel)

    @Singleton
    fun consultaChave() = KeyManagerConsultaGrpcServiceGrpc.newBlockingStub(channel)

    @Singleton
    fun listaChaves() = KeyManagerListaChavesGrpcServiceGrpc.newBlockingStub(channel)
}