package br.com.abneves.exchange.controllers.vos.responses;

import br.com.abneves.exchange.domain.Payment;
import lombok.Getter;

@Getter
public class PaymentResponse {

    private final Long paymentId;
    private final Integer productsValue;
    private final Integer totalReceived;
    private final Integer discountAmount;

    private PaymentResponse(final Long paymentId, final Integer productsValue, final Integer totalReceived, final Integer discountAmount) {
        this.paymentId = paymentId;
        this.productsValue = productsValue;
        this.totalReceived = totalReceived;
        this.discountAmount = discountAmount;
    }

    public static PaymentResponse of(final Long paymentId, final Integer productsValue, final Integer totalReceived, final Integer discountAmount) {
        return new PaymentResponse(paymentId, productsValue, totalReceived, discountAmount);
    }

    public static PaymentResponse of(final Payment payment) {
        return new PaymentResponse(payment.getPaymentId(), payment.getProductsValue(), payment.getTotalReceived(), payment.getDiscount());
    }
}
