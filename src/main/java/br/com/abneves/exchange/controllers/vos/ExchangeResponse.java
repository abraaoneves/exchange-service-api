package br.com.abneves.exchange.controllers.vos;

import br.com.abneves.exchange.domain.coin.CoinOption;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Value;

import java.util.List;
import java.util.stream.Collectors;

@Value
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ExchangeResponse {
    Integer exchangeValue;
    List<CoinResponse> options;

    protected static ExchangeResponse of(final Integer exchangeValue, final CoinOption coinOption) {
        final var coinResponses = coinOption.getOptions().stream()
                .map(coins -> {
                    final var coinsCombination = removeListBrackets(coins.toString());
                    return CoinResponse.of(coins.size(), coinsCombination);
                }).sorted()
                .collect(Collectors.toList());

        return new ExchangeResponse(exchangeValue, coinResponses);
    }

    private static String removeListBrackets(final String value) {
        return value.replaceAll("(\\[)(])", "");
    }
}
