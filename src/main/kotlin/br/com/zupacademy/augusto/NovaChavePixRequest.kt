package br.com.zupacademy.augusto

import br.com.caelum.stella.validation.CPFValidator
import br.com.zupacademy.augusto.shared.validation.ValidPixKey
import io.micronaut.core.annotation.Introspected
import io.micronaut.validation.validator.constraints.ConstraintValidatorContext
import io.micronaut.validation.validator.constraints.EmailValidator
import java.util.*
import javax.validation.constraints.NotNull
import javax.validation.constraints.Size

@ValidPixKey
@Introspected
class NovaChavePixRequest(
    @field:NotNull val tipoConta: TipoContaRequest?,
    @field:Size(max = 77) val chave: String?,
    @field:NotNull val tipoChave: TipoChaveRequest?
) {

    fun toGrpcModel(clienteId: UUID): NewPixRequest {
        return NewPixRequest.newBuilder()
            .setIdCliente(clienteId.toString())
            .setTipoChave(tipoChave?.atributoGrpc ?: TipoChave.TIPO_CHAVE_UNSPECIFIED)
            .setValorChave(chave ?: "")
            .setTipoConta(tipoConta?.atributoGrpc ?: TipoConta.TIPO_CONTA_UNSPECIFIED)
            .build()
    }

}

enum class  TipoChaveRequest(val atributoGrpc: TipoChave) {
    CPF(TipoChave.CPF)
    {
        override fun valida(valorChave: String?): Boolean {
            if (valorChave.isNullOrBlank()) {
                return false
            }
            return CPFValidator(false)
                .invalidMessagesFor(valorChave)
                .isEmpty()

        }
    },
    PHONE(TipoChave.PHONE) {
        override fun valida(valorChave: String?): Boolean {
            if (valorChave.isNullOrBlank()) {
                return false
            }
            return valorChave.matches("^\\+[1-9][0-9]\\d{1,14}\$".toRegex())
        }
    },
    EMAIL(TipoChave.EMAIL) {
        override fun valida(valorChave: String?): Boolean {
            if (valorChave.isNullOrBlank()) {
                return false
            }
            return valorChave.matches("^[a-zA-Z0-9_!#$%&’*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$".toRegex())
        }
    },
    RANDOM(TipoChave.RANDOM) {
        override fun valida(chave: String?) = chave.isNullOrBlank()
    };

    abstract fun valida(valorChave: String?): Boolean
}

enum class TipoContaRequest(val atributoGrpc: TipoConta) {
    CONTA_CORRENTE(TipoConta.CONTA_CORRENTE),
    CONTA_POUPANCA(TipoConta.CONTA_POUPANCA)
}
