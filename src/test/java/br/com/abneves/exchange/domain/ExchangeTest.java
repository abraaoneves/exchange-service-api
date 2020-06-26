package br.com.abneves.exchange.domain;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ExchangeTest {

    @Test
    public void shouldBeValidateRightExchange() {
        final var customerPaymentValue = 100;
        final var productValue = 90;

        final var payment = Payment.builder()
                .paymentId(1L)
                .productsValue(productValue)
                .totalReceived(customerPaymentValue)
                .discount(null)
                .build();

        final var exchange = Exchange.of(payment);

        Assertions.assertTrue(payment.isAllowedToExchange());
        Assertions.assertEquals(10, exchange.getExchange());
    }

    @Test
    public void shouldBeValidateRightExchangeWithSameProductValueAndCustomerValueReceive() {
        final var customerPaymentValue = 100;
        final var productValue = 100;

        final var payment = Payment.builder()
                .paymentId(1L)
                .productsValue(productValue)
                .totalReceived(customerPaymentValue)
                .discount(0)
                .build();

        final var exchange = Exchange.of(payment);

        Assertions.assertTrue(payment.isAllowedToExchange());
        Assertions.assertEquals(0, exchange.getExchange());
    }

    @Test
    public void shouldBeThrowInvalidCustomerPaymentValueForExchange() {
        final var customerPaymentValue = 90;
        final var productValue = 100;

        final var payment = Payment.builder()
                .paymentId(1L)
                .productsValue(productValue)
                .totalReceived(customerPaymentValue)
                .discount(0)
                .build();

        final var exchange = Exchange.of(payment);


        Assertions.assertThrows(Exchange.InvalidPaymentForExchange.class, exchange::getExchange);
    }
}