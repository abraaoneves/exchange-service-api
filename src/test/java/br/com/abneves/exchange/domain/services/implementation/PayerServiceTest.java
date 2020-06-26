package br.com.abneves.exchange.domain.services.implementation;

import br.com.abneves.exchange.domain.Payer;
import br.com.abneves.exchange.domain.repositories.PayerRepository;
import br.com.abneves.exchange.domain.services.implementation.PayerServiceImpl.PayerNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Collections;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
class PayerServiceTest {

    @InjectMocks
    private PayerServiceImpl service;

    @Mock
    private PayerRepository repository;

    private Payer payer;

    @BeforeEach
    private void setUp() {
        this.payer = Payer.builder()
                .payerId(1L)
                .payments(Collections.emptyList())
                .build();

        when(repository.findById(1L)).thenReturn(Optional.of(this.payer));
        when(repository.findById(2L)).thenReturn(Optional.empty());
    }


    @Test
    @DisplayName("Return payer with valid ID.")
    public void shouldBeReturnPayerWithValidId() {
        final var payer = service.getById(1L);

        assertThat(payer.getPayerId()).isEqualTo(1L);
        assertThat(payer.getPayments()).isEmpty();
    }

    @Test
    @DisplayName("Throw exception when payer with invalid ID was set.")
    public void shouldBeThrowNotFoundExceptionWithInvalidId() {
        assertThrows(PayerNotFoundException.class, () -> service.getById(2L));
    }
}