package br.com.abneves.exchange.domain.services.implementation;

import br.com.abneves.exchange.controllers.vos.requests.PaymentRequest;
import br.com.abneves.exchange.creators.PaymentRequestCreator;
import br.com.abneves.exchange.domain.Payer;
import br.com.abneves.exchange.domain.Payment;
import br.com.abneves.exchange.domain.coin.CoinCombination;
import br.com.abneves.exchange.domain.coin.CoinOption;
import br.com.abneves.exchange.domain.repositories.PaymentRepository;
import br.com.abneves.exchange.domain.services.PayerService;
import br.com.abneves.exchange.domain.services.implementation.PayerServiceImpl.PayerNotFoundException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
class PaymentServicePayTest {

    @InjectMocks
    private PaymentServiceImpl paymentService;

    @Mock
    private PaymentRepository repository;

    @Mock
    private PayerService payerService;

    private Payer payer;
    private Payment payment;
    private PaymentRequest request;

    @BeforeEach
    public void setUp() {
        this.request = PaymentRequestCreator.generate();
        this.payer = Payer.builder()
                .payerId(1L)
                .build();

        this.payment = Payment.builder()
                .payer(this.payer)
                .productsValue(this.request.getProductsValue())
                .totalReceived(this.request.getTotalReceived())
                .build();

        when(payerService.getById(1L)).thenReturn(this.payer);
        when(payerService.getById(2L))
                .thenThrow(PayerNotFoundException.class);

        when(repository.save(any())).thenReturn(this.payment);
    }

    @Test
    @DisplayName("Process Payment from valid payer and return payment.")
    public void shouldBePayFromValidPayer() {
        final var payment = paymentService.pay(1L, this.request);

        assertThat(payment.getProductsValue()).isEqualTo(125);
        assertThat(payment.getTotalReceived()).isEqualTo(135);
        assertThat(payment.getPayer()).isEqualTo(this.payer);
    }

    @Test
    @DisplayName("Throw exception Payment from invalid payer.")
    public void shouldBeThrowExceptionWhenPayFromInvalidPayerWasSet() {
        assertThrows(PayerNotFoundException.class, () -> {
            paymentService.pay(2L, this.request);
        });
    }
}