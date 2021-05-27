package br.com.zupacademy.augusto

import br.com.zupacademy.augusto.keymanager.cadastro.TipoChaveRequest
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

internal class TipoChaveRequestTest {

    @Nested
    inner class RandomTest {
        @Test
        fun `deve validar quando chave for nula ou vazia`() {
            assertTrue(TipoChaveRequest.RANDOM.valida(null))
            assertTrue(TipoChaveRequest.RANDOM.valida(""))
        }

        @Test
        fun `nao deve validar quando chave nao for vazia ou nula`() {
            assertFalse(TipoChaveRequest.RANDOM.valida("Deve estar nulo ou vazio"))
        }
    }

    @Nested
    inner class CpfTest {
        @Test
        fun `deve validar quando chave for um cpf valido`() {
            assertTrue(TipoChaveRequest.CPF.valida("90153967013"))
        }

        @Test
        fun `nao deve validar quando chave nao for um cpf valido`() {
            assertFalse(TipoChaveRequest.CPF.valida("formato inválido"))
        }

        @Test
        fun `nao deve validar quando chave for nula ou vazia`() {
            assertFalse(TipoChaveRequest.CPF.valida(null))
            assertFalse(TipoChaveRequest.CPF.valida(""))
        }
    }

    @Nested
    inner class PhoneTest {
        @Test
        fun `deve validar quando chave for um phone valido`() {
            assertTrue(TipoChaveRequest.PHONE.valida("+5537998409858"))
        }

        @Test
        fun `nao deve validar quando chave nao for um phone valido`() {
            assertFalse(TipoChaveRequest.PHONE.valida("formato inválido"))
        }

        @Test
        fun `nao deve validar quando chave for nula ou vazia`() {
            assertFalse(TipoChaveRequest.PHONE.valida(null))
            assertFalse(TipoChaveRequest.PHONE.valida(""))
        }
    }

    @Nested
    inner class EmailTest {
        @Test
        fun `deve validar quando chave for um cpf valido`() {
            assertTrue(TipoChaveRequest.EMAIL.valida("augusto@email.com"))
        }

        @Test
        fun `nao deve validar quando chave nao for um cpf valido`() {
            assertFalse(TipoChaveRequest.EMAIL.valida("@emeailformatoinvalido"))
        }

        @Test
        fun `nao deve validar quando chave for nula ou vazia`() {
            assertFalse(TipoChaveRequest.EMAIL.valida(null))
            assertFalse(TipoChaveRequest.EMAIL.valida(""))
        }
    }

}