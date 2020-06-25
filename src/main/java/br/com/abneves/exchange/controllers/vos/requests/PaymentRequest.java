package br.com.abneves.exchange.controllers.vos.requests;

import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Data
public class PaymentRequest {

    @NotNull(message = "Products value must be informed.")
    @Min(value = 1L, message = "Products value must be positive and bigger than zero.")
    private Integer productsValue;

    @NotNull(message = "Total received must be informed.")
    @Min(value = 1L, message = "Total received must be positive and bigger than zero.")
    private Integer totalReceived;
}
