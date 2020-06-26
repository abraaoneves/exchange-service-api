package br.com.abneves.exchange.controllers;

import br.com.abneves.exchange.controllers.vos.requests.PaymentRequest;
import br.com.abneves.exchange.controllers.vos.responses.ExchangeResponse;
import br.com.abneves.exchange.controllers.vos.responses.ExchangeRootResponse;
import br.com.abneves.exchange.creators.PaymentCreator;
import br.com.abneves.exchange.creators.PaymentRequestCreator;
import br.com.abneves.exchange.domain.Payer;
import br.com.abneves.exchange.domain.Payment;
import br.com.abneves.exchange.domain.services.PaymentService;
import br.com.abneves.exchange.domain.usecases.EffectPaymentUseCase;
import br.com.abneves.exchange.domain.usecases.implementation.EffectPaymentUseCaseImpl.InvalidPaymentOperationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.when;

@ExtendWith(SpringExtension.class)
public class PayerControllerTest {

    @InjectMocks
    PayerController controller;

    @Mock
    PaymentService paymentService;

    @Mock
    EffectPaymentUseCase useCase;

    private PaymentRequest request;
    private Payment payment;
    private Payer payer;

    @BeforeEach
    void setMockOutPut() {
        this.request = PaymentRequestCreator.generate();
        this.payer = Payer.builder()
                .payerId(1L)
                .payments(Collections.emptyList())
                .build();

        this.payment = Payment.builder()
                .payer(payer)
                .paymentId(1L)
                .productsValue(125)
                .totalReceived(135)
                .discount(0)
                .build();

        when(paymentService.listByPayer(1L, PageRequest.of(0, 5))).thenReturn(PaymentCreator.createPagesOfPaymentList());
        when(paymentService.listByPayer(2L, PageRequest.of(0, 5))).thenReturn(new PageImpl<>(Collections.emptyList()));
    }

    @Test
    @DisplayName("List all payments from a valid payer.")
    public void shouldBeReturnPageOfPaymentsFormPayer() {
        final var expectedListLength = 4;
        final var expectedFirstPaymentProductValue = 10;
        final var expectedFThirdPaymentTotalReceivedValue = 45;

        final var paymentsResponse = controller.listOfPaymentsFromPayer(1L, 0).getBody();

        assertThat(paymentsResponse).isNotNull();

        assertThat(paymentsResponse.getTotalElements())
                .isEqualTo(4);

        assertThat(paymentsResponse.getTotalPages())
                .isEqualTo(1);

        assertThat(paymentsResponse.getContent())
                .isNotEmpty()
                .hasSize(expectedListLength);

        assertThat(paymentsResponse.getContent()
                .get(0).getProductsValue()).isEqualTo(expectedFirstPaymentProductValue);

        assertThat(paymentsResponse.getContent()
                .get(2).getTotalReceived()).isEqualTo(expectedFThirdPaymentTotalReceivedValue);
    }


    @Test
    @DisplayName("Return empty list payments from a valid payer.")
    public void shouldBeReturnEmptyListOfPayments() {
        final var paymentsResponse = controller.listOfPaymentsFromPayer(2L, 0).getBody();
        assertThat(paymentsResponse).isNotNull();
        assertThat(paymentsResponse.getContent())
                .isEmpty();
    }

    @Test
    @DisplayName("Should be return a exchange value and combinations of coins.")
    public void shouldBeReturnExchangeValueAndListOfCoins() {
        when(useCase.execute(1L, this.request)).thenReturn(payment);

        final var request = PaymentRequestCreator.generate();
        final var response = controller.doPayment(1L, request);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);

        assertThat(response.getBody())
                .extracting(ExchangeRootResponse::getPaymentId)
                .isEqualTo(1L);

        assertThat(response.getBody())
                .extracting(ExchangeRootResponse::getProductsValue)
                .isEqualTo(125);

        assertThat(response.getBody())
                .extracting(ExchangeRootResponse::getTotalReceived)
                .isEqualTo(135);

        assertThat(response.getBody())
                .extracting(ExchangeRootResponse::getExchange)
                .extracting(ExchangeResponse::getExchangeValue)
                .isEqualTo(10);

        assertThat(response.getBody())
                .extracting(ExchangeRootResponse::getExchange)
                .extracting(ExchangeResponse::getOptions)
                .extracting(List::size)
                .isEqualTo(3);
    }


    @Test
    @DisplayName("Should be return a exchange value and one combination of coins.")
    public void shouldBeReturnExchangeValueAndListOfOneCoin() {
        final var request = new PaymentRequest();
        request.setProductsValue(11);
        request.setTotalReceived(10);

        final var payment = Payment.builder()
                .paymentId(1L)
                .totalReceived(11)
                .productsValue(10)
                .discount(0)
                .payer(this.payer)
                .build();

        when(useCase.execute(1L, request)).thenReturn(payment);

        final var response = controller.doPayment(1L, request);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(response.getBody())
                .extracting(ExchangeRootResponse::getProductsValue)
                .isEqualTo(10);

        assertThat(response.getBody())
                .extracting(ExchangeRootResponse::getTotalReceived)
                .isEqualTo(11);

        assertThat(response.getBody())
                .extracting(ExchangeRootResponse::getExchange)
                .extracting(ExchangeResponse::getExchangeValue)
                .isEqualTo(1);

        assertThat(response.getBody())
                .extracting(ExchangeRootResponse::getExchange)
                .extracting(ExchangeResponse::getOptions)
                .extracting(List::size)
                .isEqualTo(1);
    }

    @Test
    @DisplayName("Should be throw an exception when invalid values was set for customer values.")
    public void shouldBeThrowExceptionWhenInvalidExchangeWasInformed() {
        final var request = new PaymentRequest();
        request.setProductsValue(10);
        request.setTotalReceived(5);

        when(useCase.execute(1L, request)).thenThrow(InvalidPaymentOperationException.class);

        assertThrows(InvalidPaymentOperationException.class, () -> controller.doPayment(1L, request));
    }

}