package br.com.abneves.exchange.domain;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;

public class PayerTest {

    @Test
    public void shouldBeValidateRightPayment() {
        final var customerPaymentValue = 100;
        final var productValue = 90;

        final var payment = Payment.of(1L, productValue, customerPaymentValue);
        final var payer = Payer.of(1L, List.of(payment));

        Assertions.assertFalse(payer.getPayments().isEmpty());
        Assertions.assertEquals(1, payer.getPayerId());
        Assertions.assertEquals(90, payer.getPayments().get(0).getProductsValue());
    }

    @Test
    public void shouldBeValidateRightAndSameValuesForPayment() {
        final var payer = Payer.of(1L, Collections.emptyList());

        Assertions.assertTrue(payer.getPayments().isEmpty());
        Assertions.assertEquals(1, payer.getPayerId());
    }
}