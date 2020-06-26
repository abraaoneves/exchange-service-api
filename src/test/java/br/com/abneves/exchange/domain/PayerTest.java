package br.com.abneves.exchange.domain;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class PayerTest {

    @Test
    public void shouldBeValidateRightPayment() {
        final var customerPaymentValue = 100;
        final var productValue = 90;

        final var payment = Payment.builder()
                .paymentId(1L)
                .productsValue(productValue)
                .totalReceived(customerPaymentValue)
                .build();

        final var payer = Payer.builder()
                .payerId(1L)
                .payments(List.of(payment))
                .build();

        Assertions.assertFalse(payer.getPayments().isEmpty());
        assertEquals(1, payer.getPayerId());
        assertEquals(90, payer.getPayments().get(0).getProductsValue());
    }

    @Test
    public void shouldBeValidateRightAndSameValuesForPayment() {
        final var payer = Payer.builder()
                .payerId(1L)
                .payments(Collections.emptyList())
                .build();

        assertTrue(payer.getPayments().isEmpty());
        assertEquals(1, payer.getPayerId());
    }


    @Test
    public void shouldBeIncrementTotalOfPayments() {
        final var payer = Payer.builder()
                .payerId(1L)
                .totalOfPayments(null)
                .payments(Collections.emptyList())
                .build();

        assertTrue(payer.getPayments().isEmpty());
        assertEquals(1, payer.getPayerId());
        assertEquals(0, payer.getTotalOfPayments());

        payer.incrementTotalOfPayments();
        assertEquals(1, payer.getTotalOfPayments());

        payer.incrementTotalOfPayments();
        assertEquals(2, payer.getTotalOfPayments());

    }
}