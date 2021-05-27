package br.com.zupacademy.augusto.keymanager.shared.validation

import br.com.zupacademy.augusto.keymanager.cadastro.NovaChavePixRequest
import io.micronaut.core.annotation.AnnotationValue
import io.micronaut.validation.validator.constraints.ConstraintValidator
import io.micronaut.validation.validator.constraints.ConstraintValidatorContext
import javax.inject.Singleton
import javax.validation.Constraint
import kotlin.reflect.KClass

@MustBeDocumented
@Target(AnnotationTarget.CLASS, AnnotationTarget.TYPE)
@Retention(AnnotationRetention.RUNTIME)
@Constraint(validatedBy = [ ValidPixKeyValidator::class ])
annotation class ValidPixKey (
    val message: String = "Chave inválida para tipo \${validatedValue.tipoChave}.",
    val groups: Array<KClass<Any>> = [],
    val payload: Array<KClass<Any>> = []
)

@Singleton
class ValidPixKeyValidator: ConstraintValidator<ValidPixKey, NovaChavePixRequest> {
    override fun isValid(
        value: NovaChavePixRequest?,
        annotationMetadata: AnnotationValue<ValidPixKey>,
        context: ConstraintValidatorContext
    ): Boolean {
        if (value?.tipoChave == null) return false

        return value.tipoChave.valida(value.chave)
    }
}
