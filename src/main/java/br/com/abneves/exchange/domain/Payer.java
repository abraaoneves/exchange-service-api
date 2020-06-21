package br.com.abneves.exchange.domain;

import java.util.List;

/**
 * Payer
 *
 * @author Abraao Neves
 * @version 0.0.1 21-06-2020
 */
public class Payer {

    private final Long payerId;
    private final List<Payment> payments;

    private Payer(final Long payerId, final List<Payment> payments) {
        this.payerId = payerId;
        this.payments = payments;
    }

    /**
     * Static construct instance class with basic values
     *
     * @param paymentId
     * @param payments
     * @return
     */
    public static Payer of(final Long paymentId, final List<Payment> payments) {
        return new Payer(paymentId, payments);
    }

    public Long getPayerId() {
        return payerId;
    }

    public List<Payment> getPayments() {
        return payments;
    }
}
