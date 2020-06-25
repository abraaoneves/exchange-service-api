package br.com.abneves.exchange.controllers.vos.requests;

import br.com.abneves.exchange.domain.Payment;
import lombok.Getter;

@Getter
public class PaymentResponse {

    private final Long paymentId;
    private final Integer productsValue;
    private final Integer totalReceived;

    private PaymentResponse(final Long paymentId, final Integer productsValue, final Integer totalReceived) {
        this.paymentId = paymentId;
        this.productsValue = productsValue;
        this.totalReceived = totalReceived;
    }

    public static PaymentResponse of(final Long paymentId, final Integer productsValue, final Integer totalReceived) {
        return new PaymentResponse(paymentId, productsValue, totalReceived);
    }

    public static PaymentResponse of(final Payment payment) {
        return new PaymentResponse(payment.getPaymentId(), payment.getProductsValue(), payment.getTotalReceived());
    }
}
