package br.com.abneves.exchange.domain.services.implementation;

import br.com.abneves.exchange.domain.Payment;
import br.com.abneves.exchange.domain.services.PaymentService;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class PaymentServiceImpl implements PaymentService {

    @Override
    public List<Payment> listByPayer(Long payerId) {
        return Collections.emptyList();
    }
}
