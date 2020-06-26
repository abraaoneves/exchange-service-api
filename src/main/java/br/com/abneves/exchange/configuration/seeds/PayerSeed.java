package br.com.abneves.exchange.configuration.seeds;

import br.com.abneves.exchange.domain.Payer;
import br.com.abneves.exchange.domain.repositories.PayerRepository;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class PayerSeed {

    private final PayerRepository repository;

    @Bean
    public void setUpPayers() {
        if (repository.findAll().isEmpty()) {
            final var payer = Payer.builder()
                    .totalOfPayments(0)
                    .build();

            repository.save(payer);
        }
    }
}
