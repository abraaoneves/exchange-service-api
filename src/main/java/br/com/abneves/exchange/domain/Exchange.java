package br.com.abneves.exchange.domain;

import br.com.abneves.exchange.domain.coin.CoinOption;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Exchange
 *
 * @author Abraao Neves
 * @version 0.0.1 21-06-2020
 */
public class Exchange {

    private final Payment payment;

    private Exchange(Payment payment) {
        this.payment = payment;
    }

    public static Exchange of(final Payment payment) {
        return new Exchange(payment);
    }

    public Integer getExchange() {
        if (payment.isAllowedToExchange()) {
            return payment.getTotalReceived() - payment.getProductsValue();
        }

        throw new InvalidPaymentForExchange("Invalid value for exchange.");
    }

    public CoinOption getCoinOption() {
        return CoinOption.of(this.getExchange());
    }

    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    public static class InvalidPaymentForExchange extends RuntimeException {
        public InvalidPaymentForExchange(String message) {
            super(message);
        }
    }
}
