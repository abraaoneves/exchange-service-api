package br.com.abneves.exchange.controllers.vos;

import br.com.abneves.exchange.controllers.vos.responses.PaymentResponse;
import br.com.abneves.exchange.domain.Payment;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class PaymentResponseTest {

    @Test
    @DisplayName("Build generic response")
    public void shouldBeGenerateResponseByValues() {
        final var response = PaymentResponse.of(1L, 10, 11);

        assertThat(response.getPaymentId())
                .isEqualTo(1L);

        assertThat(response.getProductsValue())
                .isEqualTo(10);

        assertThat(response.getTotalReceived())
                .isEqualTo(11);
    }

    @Test
    @DisplayName("Build generic response")
    public void shouldBeGenerateResponseByDomain() {
        final var payment = Payment.builder()
                .paymentId(1L)
                .payer(null)
                .productsValue(10)
                .totalReceived(11)
                .build();

        final var response = PaymentResponse.of(payment);

        assertThat(response.getPaymentId())
                .isEqualTo(1L);

        assertThat(response.getProductsValue())
                .isEqualTo(10);

        assertThat(response.getTotalReceived())
                .isEqualTo(11);
    }
}