package br.com.abneves.exchange.domain.services.implementation;

import br.com.abneves.exchange.domain.Payment;
import br.com.abneves.exchange.domain.repositories.PaymentRepository;
import br.com.abneves.exchange.domain.services.PaymentService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository repository;

    @Override
    public Page<Payment> listByPayer(Long payerId, Pageable pageable) {
        return repository.findAllByPayerPayerId(payerId, pageable);
    }
}
