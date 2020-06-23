package br.com.abneves.exchange.creators;

import br.com.abneves.exchange.domain.Payment;

import java.util.List;

public class PaymentCreator {

    public static List<Payment> createListOfPayments() {
        return List.of(
                createPayment(1L, 10, 15),
                createPayment(2L, 20, 25),
                createPayment(3L, 30, 45),
                createPayment(4L, 40, 45));
    }

    private static Payment createPayment(final Long id, final int productValue, final int customerPaidValue) {
        return Payment.builder()
                .paymentId(id)
                .productsValue(productValue)
                .totalReceived(customerPaidValue)
                .build();
    }
}
