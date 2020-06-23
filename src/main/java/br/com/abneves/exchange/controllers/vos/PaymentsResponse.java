package br.com.abneves.exchange.controllers.vos;

import java.util.List;

public class PaymentsResponse {

    private final List<PaymentResponse> payments;

    private PaymentsResponse(final List<PaymentResponse> payments) {
        this.payments = payments;
    }

    public static PaymentsResponse of(final List<PaymentResponse> payments) {
        return new PaymentsResponse(payments);
    }

    public void addPayment(final PaymentResponse payment) {
        this.payments.add(payment);
    }

    public List<PaymentResponse> getPayments() {
        return payments;
    }
}
