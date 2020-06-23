package br.com.abneves.exchange.domain.repositories;

import br.com.abneves.exchange.domain.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
}
