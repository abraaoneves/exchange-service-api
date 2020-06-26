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
    public Page<Payment> listByPayer(final Long payerId, final Pageable pageable) {
        return repository.findAllByPayerPayerId(payerId, pageable);
    }

    @Override
    public Payment save(final Payment payment) {
        return repository.save(payment);
    }
}
