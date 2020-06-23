package br.com.abneves.exchange.controllers;

import br.com.abneves.exchange.controllers.vos.PaymentResponse;
import br.com.abneves.exchange.controllers.vos.PaymentsResponse;
import br.com.abneves.exchange.domain.services.PaymentService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/payers", produces = MediaType.APPLICATION_JSON_VALUE)
public class PayerController {

    private final PaymentService paymentService;

    public PayerController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @GetMapping("/{payerId}/payments")
    public ResponseEntity<PaymentsResponse> listOfPaymentsFromPayer(@PathVariable("payerId") final Long payer) {
        final var payments = paymentService.listByPayer(payer);
        final var response = payments.stream().map(PaymentResponse::of).collect(Collectors.toList());

        return ResponseEntity.ok(PaymentsResponse.of(response));
    }
}
