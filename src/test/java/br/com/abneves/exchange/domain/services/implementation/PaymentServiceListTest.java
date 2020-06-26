package br.com.abneves.exchange.domain.services.implementation;

import br.com.abneves.exchange.creators.PaymentCreator;
import br.com.abneves.exchange.domain.Payment;
import br.com.abneves.exchange.domain.repositories.PaymentRepository;
import br.com.abneves.exchange.domain.services.PayerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.when;

@ExtendWith(SpringExtension.class)
public class PaymentServiceListTest {

    @InjectMocks
    private PaymentServiceImpl paymentService;

    @Mock
    private PaymentRepository repository;

    private static final Pageable PAGEABLE = PageRequest.of(0, 2);

    @BeforeEach
    public void setUp() {
        final var payments = PaymentCreator.createPagesOfPaymentList();
        when(repository.findAllByPayerPayerId(1L, PAGEABLE)).thenReturn(payments);
        when(repository.findAllByPayerPayerId(2L, PAGEABLE)).thenReturn(new PageImpl<>(Collections.emptyList()));
    }

    @Test
    @DisplayName("Return a list of payments by payer id")
    public void listPaymentsByPayer_whenPayerIsInformed_listOfPaymentsWithPaginationWasReturn() {
        final var payments = paymentService.listByPayer(1L, PAGEABLE);

        assertThat(payments.getTotalElements()).isEqualTo(4);
        assertThat(payments.getContent()).isNotEmpty()
                .hasSize(4)
                .first()
                .extracting(Payment::getPaymentId)
                .isEqualTo(1L);
    }

    @Test
    @DisplayName("Return a empty list of payments for invalid payer id")
    public void listPaymentsByPayer_whenInvalidPayerIsInformed_emptyListWasReturn() {
        final var payments = paymentService.listByPayer(2L, PAGEABLE);

        assertThat(payments.getTotalElements()).isEqualTo(0);
        assertThat(payments.getContent())
                .isEmpty();
    }

}