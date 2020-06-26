package br.com.abneves.exchange.domain.services.implementation;

import br.com.abneves.exchange.domain.Payer;
import br.com.abneves.exchange.domain.repositories.PayerRepository;
import br.com.abneves.exchange.domain.services.PayerService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ResponseStatus;

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

    @ResponseStatus(HttpStatus.NOT_FOUND)
    public static class PayerNotFoundException extends RuntimeException {
        public PayerNotFoundException(String message) {
            super(message);
        }
    }
}
