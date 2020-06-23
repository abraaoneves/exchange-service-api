package br.com.abneves.exchange.domain;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class PaymentTest {

    @Test
    public void shouldBeValidateRightPayment() {
        final var customerPaymentValue = 100;
        final var productValue = 90;

        final var payment = Payment.builder()
                .paymentId(1L)
                .productsValue(productValue)
                .totalReceived(customerPaymentValue)
                .build();

        Assertions.assertTrue(payment.isAllowedToExchange());
        Assertions.assertEquals(100, payment.getTotalReceived());
        Assertions.assertEquals(90, payment.getProductsValue());
    }

    @Test
    public void shouldBeValidateRightAndSameValuesForPayment() {
        final var customerPaymentValue = 100;
        final var productValue = 100;

        final var payment = Payment.builder()
                .paymentId(1L)
                .productsValue(productValue)
                .totalReceived(customerPaymentValue)
                .build();

        Assertions.assertTrue(payment.isAllowedToExchange());
        Assertions.assertEquals(100, payment.getTotalReceived());
        Assertions.assertEquals(100, payment.getProductsValue());
    }

    @Test
    public void shouldBeValidateWrongPayment() {
        final var customerPaymentValue = 90;
        final var productValue = 100;

        final var payment = Payment.builder()
                .paymentId(1L)
                .productsValue(productValue)
                .totalReceived(customerPaymentValue)
                .build();

        Assertions.assertFalse(payment.isAllowedToExchange());
        Assertions.assertEquals(90, payment.getTotalReceived());
        Assertions.assertEquals(100, payment.getProductsValue());
    }
}