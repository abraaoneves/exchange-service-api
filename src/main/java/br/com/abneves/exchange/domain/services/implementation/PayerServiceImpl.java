package br.com.abneves.exchange.domain.services.implementation;

import br.com.abneves.exchange.configuration.exceptions.NotFoundException;
import br.com.abneves.exchange.domain.Payer;
import br.com.abneves.exchange.domain.repositories.PayerRepository;
import br.com.abneves.exchange.domain.services.PayerService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
class PayerServiceImpl implements PayerService {

    private final PayerRepository repository;

    @Override
    public Payer getById(final Long payerId) {
        final var failMessage = String.format("Payer (%s) not found", payerId);
        return repository
                .findById(payerId)
                .orElseThrow(() -> new PayerNotFoundException(failMessage));
    }

    @Override
    public void updateTotalPayments(final Payer payer) {
        payer.incrementTotalOfPayments();
        repository.save(payer);
    }

    public static class PayerNotFoundException extends NotFoundException {
        public PayerNotFoundException(String message) {
            super(message);
        }
    }
}
