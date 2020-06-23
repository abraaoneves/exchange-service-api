package br.com.abneves.exchange.controllers;

import br.com.abneves.exchange.controllers.decorators.PaymentPageDecorator;
import br.com.abneves.exchange.controllers.vos.PaymentResponse;
import br.com.abneves.exchange.domain.services.PaymentService;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/payers", produces = MediaType.APPLICATION_JSON_VALUE)
public class PayerController {

    private static final int TOTAL_OF_PAGES = 5;

    private final PaymentService paymentService;

    public PayerController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @GetMapping("/{payerId}/payments")
    public ResponseEntity<PaymentPageDecorator<PaymentResponse>> listOfPaymentsFromPayer(@PathVariable("payerId") final Long payer,
                                                                                         @RequestParam(value = "page", defaultValue = "0") int page) {
        final var pageable = PageRequest.of(page, TOTAL_OF_PAGES);
        final var paymentResponseList = paymentService.listByPayer(payer, pageable)
                .stream()
                .map(PaymentResponse::of)
                .collect(Collectors.toList());

        final var paymentPageDecorator = new PaymentPageDecorator<>(new PageImpl<>(paymentResponseList));
        return ResponseEntity.ok(paymentPageDecorator);
    }
}
