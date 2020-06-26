package br.com.abneves.exchange.controllers;

import br.com.abneves.exchange.controllers.decorators.PaymentPageDecorator;
import br.com.abneves.exchange.controllers.vos.requests.PaymentRequest;
import br.com.abneves.exchange.controllers.vos.responses.ExchangeRootResponse;
import br.com.abneves.exchange.controllers.vos.responses.PaymentResponse;
import br.com.abneves.exchange.domain.Exchange;
import br.com.abneves.exchange.domain.services.PaymentService;
import br.com.abneves.exchange.domain.usecases.EffectPaymentUseCase;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.stream.Collectors;

@RestController
@AllArgsConstructor
@RequestMapping(value = "/payers", produces = MediaType.APPLICATION_JSON_VALUE)
public class PayerController {

    private static final int TOTAL_OF_PAGES = 5;

    private final PaymentService paymentService;
    private final EffectPaymentUseCase effectPaymentUseCase;

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

    @PostMapping(value = "/{payerId}/payments", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ExchangeRootResponse> doPayment(@PathVariable("payerId") final Long payer,
                                                          @RequestBody @Valid final PaymentRequest paymentRequest) {
        final var payment = effectPaymentUseCase.execute(payer, paymentRequest);

        final var exchange = Exchange.of(payment);
        final var response = ExchangeRootResponse.builder()
                .exchange(exchange)
                .paymentId(payment.getPaymentId())
                .productsValue(payment.getProductsValue())
                .totalReceived(payment.getTotalReceived())
                .discountAmount(payment.getDiscount())
                .build();

        return ResponseEntity.ok(response);
    }

}
