package br.com.abneves.exchange.creators;

import br.com.abneves.exchange.domain.Payment;

import java.util.List;

public class PaymentCreator {

    public static List<Payment> createListOfPayments() {
        return List.of(
                Payment.of(1L, 10, 15),
                Payment.of(2L, 20, 25),
                Payment.of(3L, 30, 45),
                Payment.of(4L, 40, 45));
    }
}
