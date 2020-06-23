package br.com.abneves.exchange.controllers.vos;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.assertj.core.api.Assertions.assertThat;

public class PaymentsResponseTest {

    @Test
    @DisplayName("Should be add payment response to empty list from PaymentsResponse.")
    public void addPaymentResponse_AddPaymentResponseToList() {
        final var paymentResponse = PaymentResponse.of(1L, 10, 20);
        final var payments = PaymentsResponse.of(new ArrayList<>());

        assertThat(payments.getPayments()).isEmpty();

        payments.addPayment(paymentResponse);

        assertThat(payments.getPayments())
                .isNotEmpty()
                .hasSize(1)
                .first().isEqualTo(paymentResponse)
                .extracting(PaymentResponse::getPaymentId).isEqualTo(1L);
    }
}