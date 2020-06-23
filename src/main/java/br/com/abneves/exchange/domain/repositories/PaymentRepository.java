package br.com.abneves.exchange.domain.repositories;

import br.com.abneves.exchange.domain.Payment;
import org.springframework.data.annotation.ReadOnlyProperty;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository extends JpaRepository<Payment, Long> {

    @ReadOnlyProperty
    Page<Payment> findAllByPayerPayerId(final Long payerId, Pageable pageable);
}
