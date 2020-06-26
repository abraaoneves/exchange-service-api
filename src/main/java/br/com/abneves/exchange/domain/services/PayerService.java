package br.com.abneves.exchange.domain.services;

import br.com.abneves.exchange.domain.Payer;

public interface PayerService {
    Payer getById(final Long payerId);

    void updateTotalPayments(final Payer payer);
}
