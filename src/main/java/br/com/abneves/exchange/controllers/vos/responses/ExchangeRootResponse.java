package br.com.abneves.exchange.controllers.vos.responses;

import br.com.abneves.exchange.domain.Exchange;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ExchangeRootResponse {
    Long paymentId;
    Integer productsValue;
    Integer totalReceived;
    ExchangeResponse exchange;

    public static class ExchangeRootResponseBuilder {

        ExchangeResponse exchange;

        public ExchangeRootResponseBuilder exchange(final Exchange exchange) {
            this.exchange = ExchangeResponse.of(exchange.getExchange(), exchange.getCoinOption());
            return this;
        }
    }
}
