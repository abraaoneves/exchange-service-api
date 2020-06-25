package br.com.abneves.exchange.domain.services;

import br.com.abneves.exchange.controllers.vos.PaymentRequest;
import br.com.abneves.exchange.domain.Payment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PaymentService {
    Page<Payment> listByPayer(final Long payerId, final Pageable pageable);
    Payment pay(final Long payerId, final PaymentRequest paymentRequest);
}
