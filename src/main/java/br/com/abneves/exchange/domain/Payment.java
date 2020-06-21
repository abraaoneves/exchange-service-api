package br.com.abneves.exchange.domain;

/**
 * Payment
 *
 * @author Abraao Neves
 * @version 0.0.1 21-06-2020
 */
public class Payment {

    private final Long paymentId;
    private final Integer productsValue;
    private final Integer totalReceived;

    Payment(final Long paymentId, final Integer productsValue, final Integer totalReceived) {
        this.paymentId = paymentId;
        this.productsValue = productsValue;
        this.totalReceived = totalReceived;
    }

    /**
     * Static construct instance class with basic values
     *
     * @param paymentId
     * @param productsValue
     * @param totalReceived
     * @return
     */
    public static Payment of(final Long paymentId, final Integer productsValue, final Integer totalReceived) {
        return new Payment(paymentId, productsValue, totalReceived);
    }

    protected boolean isAllowedToExchange() {
        return (this.totalReceived > 0 && this.totalReceived >= this.productsValue);
    }

    protected Integer getProductsValue() {
        return productsValue;
    }

    protected Integer getTotalReceived() {
        return totalReceived;
    }

    public Long getPaymentId() {
        return paymentId;
    }
}
