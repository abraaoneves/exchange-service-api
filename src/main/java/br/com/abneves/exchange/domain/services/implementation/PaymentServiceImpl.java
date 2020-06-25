package br.com.abneves.exchange.domain.services.implementation;

import br.com.abneves.exchange.controllers.vos.requests.PaymentRequest;
import br.com.abneves.exchange.domain.Payer;
import br.com.abneves.exchange.domain.Payment;
import br.com.abneves.exchange.domain.repositories.PaymentRepository;
import br.com.abneves.exchange.domain.services.PayerService;
import br.com.abneves.exchange.domain.services.PaymentService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository repository;
    private final PayerService payerService;

    @Override
    public Page<Payment> listByPayer(final Long payerId, final Pageable pageable) {
        return repository.findAllByPayerPayerId(payerId, pageable);
    }

    @Override
    public Payment pay(final Long payerId, final PaymentRequest paymentRequest) {
        final var payer = payerService.getById(payerId);
        return save(payer, paymentRequest.getProductsValue(), paymentRequest.getTotalReceived());
    }

    private Payment save(final Payer payer, final Integer productsValue, final Integer totalReceived) {
        final var payment = Payment.builder()
                .payer(payer)
                .productsValue(productsValue)
                .totalReceived(totalReceived)
                .build();

        return repository.save(payment);
    }
}
