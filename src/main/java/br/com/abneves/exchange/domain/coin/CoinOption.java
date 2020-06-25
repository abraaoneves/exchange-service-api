package br.com.abneves.exchange.domain.coin;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Value;

import java.util.List;

@Value
@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class CoinOption {

    Integer exchange;
    List<List<Integer>> options;

    public static CoinOption of(final Integer exchange) {
        final var coinPartition = CoinCombination.of();
        coinPartition.findCoinsCombinations(exchange);
        return new CoinOption(exchange, coinPartition.getCombinations());
    }
}
