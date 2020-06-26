package br.com.abneves.exchange.domain.services.implementation;

import br.com.abneves.exchange.controllers.vos.requests.PaymentRequest;
import br.com.abneves.exchange.creators.PaymentRequestCreator;
import br.com.abneves.exchange.domain.Payer;
import br.com.abneves.exchange.domain.Payment;
import br.com.abneves.exchange.domain.services.PayerService;
import br.com.abneves.exchange.domain.services.PaymentService;
import br.com.abneves.exchange.domain.services.implementation.PayerServiceImpl.PayerNotFoundException;
import br.com.abneves.exchange.domain.usecases.GiveDiscountUseCase;
import br.com.abneves.exchange.domain.usecases.implementation.EffectPaymentUseCaseImpl;
import br.com.abneves.exchange.domain.usecases.implementation.EffectPaymentUseCaseImpl.InvalidPaymentOperationException;
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
class EffectPaymentUseCaseTest {

    @InjectMocks
    private EffectPaymentUseCaseImpl effectPaymentUseCase;

    @Mock
    private PaymentService paymentService;

    @Mock
    private PayerService payerService;

    @Mock
    private GiveDiscountUseCase giveDiscountUseCase;

    private Payer payer;
    private Payment payment;
    private PaymentRequest request;

    @BeforeEach
    public void setUp() {
        this.request = PaymentRequestCreator.generate();
        this.payer = Payer.builder()
                .payerId(1L)
                .totalOfPayments(0)
                .build();

        this.payment = Payment.builder()
                .payer(this.payer)
                .productsValue(this.request.getProductsValue())
                .totalReceived(this.request.getTotalReceived())
                .build();

        when(payerService.getById(1L)).thenReturn(this.payer);
        when(payerService.getById(2L)).thenThrow(PayerNotFoundException.class);
        when(paymentService.save(any())).thenReturn(this.payment);
        when(giveDiscountUseCase.getDiscount(0, 125)).thenReturn(0);
    }

    @Test
    @DisplayName("Process Payment from valid payer and return payment.")
    public void shouldBePayFromValidPayer() {
        final var payment = effectPaymentUseCase.execute(1L, this.request);

        assertThat(payment.getProductsValue()).isEqualTo(125);
        assertThat(payment.getTotalReceived()).isEqualTo(135);
        assertThat(payment.getPayer()).isEqualTo(this.payer);
    }

    @Test
    @DisplayName("Throw exception Payment from invalid payer.")
    public void shouldBeThrowExceptionWhenPayFromInvalidPayerWasSet() {
        assertThrows(PayerNotFoundException.class, () -> effectPaymentUseCase.execute(2L, this.request));
    }

    @Test
    @DisplayName("Throw exception Payment from invalid payment values.")
    public void shouldBeThrowExceptionWhenPaymentsHasInvalidValues() {
        assertThrows(InvalidPaymentOperationException.class, () -> {
            final var request = PaymentRequestCreator.generate(10, 9);
            effectPaymentUseCase.execute(1L, request);
        });
    }
}