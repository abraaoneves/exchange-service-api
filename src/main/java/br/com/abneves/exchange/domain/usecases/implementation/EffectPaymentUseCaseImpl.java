package br.com.abneves.exchange.domain.usecases.implementation;

import br.com.abneves.exchange.configuration.annotations.UseCase;
import br.com.abneves.exchange.controllers.vos.requests.PaymentRequest;
import br.com.abneves.exchange.domain.Payment;
import br.com.abneves.exchange.domain.services.PayerService;
import br.com.abneves.exchange.domain.services.PaymentService;
import br.com.abneves.exchange.domain.usecases.EffectPaymentUseCase;
import br.com.abneves.exchange.domain.usecases.GiveDiscountUseCase;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.ResponseStatus;

@UseCase
@AllArgsConstructor
public class EffectPaymentUseCaseImpl implements EffectPaymentUseCase {

    private final PaymentService paymentService;
    private final PayerService payerService;
    private final GiveDiscountUseCase giveDiscountUseCase;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Payment execute(Long payerId, PaymentRequest paymentRequest) {
        this.validatePayment(paymentRequest);

        final var payer = payerService.getById(payerId);

        final var discount = giveDiscountUseCase.getDiscount(payer.getTotalOfPayments(), paymentRequest.getProductsValue());

        final var payment = Payment.builder()
                .payer(payer)
                .productsValue(paymentRequest.getProductsValue())
                .totalReceived(paymentRequest.getTotalReceived())
                .discount(discount)
                .build();

        payerService.updateTotalPayments(payer);

        return paymentService.save(payment);
    }

    private void validatePayment(final PaymentRequest paymentRequest) {
        if (paymentRequest.getProductsValue() > paymentRequest.getTotalReceived()) {
            throw new InvalidPaymentOperationException();
        }
    }

    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    public static class InvalidPaymentOperationException extends IllegalArgumentException {
        private static final String MESSAGE = "Invalid payment, the total received must be higher than products value.";

        public InvalidPaymentOperationException() {
            super(MESSAGE);
        }
    }
}
