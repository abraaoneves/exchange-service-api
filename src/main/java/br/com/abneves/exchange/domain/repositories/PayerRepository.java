package br.com.abneves.exchange.domain.repositories;

import br.com.abneves.exchange.domain.Payer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PayerRepository extends JpaRepository<Payer, Long> {
}
