package br.com.abneves.exchange.domain.coin;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.ArrayList;
import java.util.List;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class CoinCombination {
    private static final List<Integer> COINS_VALUE_DELIMITER = List.of(1, 2, 3, 5, 8);
    private static final Integer MAX_STEPS_FOR_EXCHANGE = 3;

    private List<List<Integer>> combinations;

    public static CoinCombination of() {
        return new CoinCombination(new ArrayList<>());
    }

    public void findCoinsCombinations(Integer exchangeValue) {
        if (exchangeValue <= 0) {
            throw new CoinExchangeCombinationInvalid();
        }

        if (exchangeValue >= 1000) {
            throw new CoinExchangeCombinationInvalid("This exchange value is invalid, this value is too large for simple coins.");
        }

        if (exchangeValue == 1) {
            this.combinations.add(List.of(1));
        }

        this.generateCombinations(4, exchangeValue, new ArrayList<>());
    }

    private void generateCombinations(final Integer start, final Integer target, final List<Integer> combination) {
        if (target == 0) {
            if (combinations.size() == MAX_STEPS_FOR_EXCHANGE) {
                return;
            }

            combinations.add(new ArrayList<>(combination));
        }

        if (target < 0) {
            return;
        }

        for (int i = start; i > 0; i--) {
            combination.add(COINS_VALUE_DELIMITER.get(i));
            generateCombinations(i, target - COINS_VALUE_DELIMITER.get(i), combination);
            combination.remove(combination.size() - 1);
        }
    }

    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    public static class CoinExchangeCombinationInvalid extends IllegalArgumentException {
        private static final String MESSAGE = "Invalid exchange value for partition.";

        public CoinExchangeCombinationInvalid() {
            super(MESSAGE);
        }

        public CoinExchangeCombinationInvalid(String message) {
            super(message);
        }
    }
}
