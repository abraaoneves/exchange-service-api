package br.com.abneves.exchange.domain.services.implementation;

import br.com.abneves.exchange.domain.Payment;
import br.com.abneves.exchange.domain.repositories.PayerRepository;
import br.com.abneves.exchange.domain.services.PaymentService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.List;

@Service
public class PaymentServiceImpl implements PaymentService {

    private final PayerRepository repository;

    public PaymentServiceImpl(PayerRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<Payment> listByPayer(Long payerId) {
        final var payer = repository
                .findById(payerId)
                .orElseThrow(() -> new PayerNotFoundException("Payer not found."));

        return payer.getPayments();
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    public static class PayerNotFoundException extends RuntimeException {
        public PayerNotFoundException(String message) {
            super(message);
        }
    }
}
