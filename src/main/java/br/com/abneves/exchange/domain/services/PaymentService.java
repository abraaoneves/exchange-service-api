package br.com.abneves.exchange.domain.services;

import br.com.abneves.exchange.domain.Payment;

import java.util.List;

public interface PaymentService {
    List<Payment> listByPayer(final Long payerId);
}
