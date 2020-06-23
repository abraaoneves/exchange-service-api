package br.com.abneves.exchange.controllers;

import br.com.abneves.exchange.creators.PaymentCreator;
import br.com.abneves.exchange.domain.services.PaymentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.when;

@ExtendWith(SpringExtension.class)
public class PayerControllerTest {

    @InjectMocks
    PayerController controller;

    @Mock
    PaymentService service;

    @BeforeEach
    void setMockOutPut() {
        when(service.listByPayer(1L, PageRequest.of(0, 5))).thenReturn(PaymentCreator.createListOfPayments());
    }

    @Test
    @DisplayName("List all payments from a valid payer.")
    public void listAllPayments_ReturnListOfPaymentsForPayer_WhenPayerExists() {
        final var expectedListLength = 4;
        final var expectedFirstPaymentProductValue = 10;
        final var expectedFThirdPaymentTotalReceivedValue = 45;

        final var paymentsResponse = controller.listOfPaymentsFromPayer(1L, 0).getBody();

        assertThat(paymentsResponse).isNotNull();

        assertThat(paymentsResponse.getContent())
                .isNotEmpty()
                .hasSize(expectedListLength);

        assertThat(paymentsResponse.getContent()
                .get(0).getProductsValue()).isEqualTo(expectedFirstPaymentProductValue);

        assertThat(paymentsResponse.getContent()
                .get(2).getTotalReceived()).isEqualTo(expectedFThirdPaymentTotalReceivedValue);
    }

}