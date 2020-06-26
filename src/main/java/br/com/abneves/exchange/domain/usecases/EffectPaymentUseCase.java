package br.com.abneves.exchange.domain.usecases;

import br.com.abneves.exchange.controllers.vos.requests.PaymentRequest;
import br.com.abneves.exchange.domain.Payment;

@FunctionalInterface
public interface EffectPaymentUseCase {
    Payment execute(final Long payerId, final PaymentRequest paymentRequest);
}
