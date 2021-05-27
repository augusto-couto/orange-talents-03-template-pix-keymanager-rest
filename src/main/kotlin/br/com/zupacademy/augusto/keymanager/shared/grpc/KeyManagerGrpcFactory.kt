package br.com.zupacademy.augusto.keymanager.shared.grpc

import br.com.zupacademy.augusto.KeymanagerCadastraGrpcServiceGrpc
import io.grpc.ManagedChannel
import io.micronaut.context.annotation.Factory
import io.micronaut.grpc.annotation.GrpcChannel
import javax.inject.Singleton

@Factory
class KeyManagerGrpcFactory(@GrpcChannel("keyManager") val channel: ManagedChannel) {

    @Singleton
    fun cadastraChave() = KeymanagerCadastraGrpcServiceGrpc.newBlockingStub(channel)
}