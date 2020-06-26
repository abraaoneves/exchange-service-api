package br.com.abneves.exchange.creators;

import br.com.abneves.exchange.controllers.vos.requests.PaymentRequest;

public class PaymentRequestCreator {

    public static PaymentRequest generate() {
        return generate(125, 135);
    }

    public static PaymentRequest generate(Integer vProducts, Integer vCustomerPay) {
        final var request = new PaymentRequest();
        request.setProductsValue(vProducts);
        request.setTotalReceived(vCustomerPay);
        return request;
    }
}
