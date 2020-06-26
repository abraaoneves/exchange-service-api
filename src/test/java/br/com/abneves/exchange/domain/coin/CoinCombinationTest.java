package br.com.abneves.exchange.domain.coin;

import org.junit.jupiter.api.*;

import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.Assertions.assertThat;

class CoinCombinationTest {

    @Test
    @Timeout(value = 5, unit = TimeUnit.MILLISECONDS)
    @DisplayName("Return a list of 3 combinations for valid exchange value")
    public void listOfCombination_shouldBeReturnListOfThreeCombinationsOfCoins_whenValidExchangeValueIsUsed() {
        final var coinCombination = CoinCombination.of();
        coinCombination.findCoinsCombinations(10);

        assertThat(coinCombination.getCombinations())
                .hasSize(3)
                .first()
                .extracting(List::size).isEqualTo(2);
    }

    @Test
    @Timeout(value = 10, unit = TimeUnit.MILLISECONDS)
    @DisplayName("Should throw exception when 1 for exchange value is set.")
    public void shouldThrowException_whenOneExchangeValueIsSet() {
        final var coinCombination = CoinCombination.of();
        coinCombination.findCoinsCombinations(1);

        assertThat(coinCombination.getCombinations())
                .hasSize(1)
                .first()
                .extracting(List::size).isEqualTo(1);
    }

    @Test
    @Timeout(value = 150, unit = TimeUnit.MILLISECONDS)
    @DisplayName("Return a list of 3 combinations for valid and big exchange value")
    public void listOfCombination_shouldBeReturnListOfThreeCombinationsOfCoins_whenValidAndBigExchangeValueIsUsed() {
        final var coinCombination = CoinCombination.of();
        coinCombination.findCoinsCombinations(500);

        assertThat(coinCombination.getCombinations())
                .hasSize(3);
    }

    @Test
    @Timeout(value = 1000, unit = TimeUnit.MILLISECONDS)
    @DisplayName("Return a list of 3 combinations for valid and biggest exchange value")
    public void listOfCombination_shouldBeReturnListOfThreeCombinationsOfCoins_whenValidAndBiggestExchangeValueIsUsed() {
        final var coinCombination = CoinCombination.of();
        coinCombination.findCoinsCombinations(849);

        assertThat(coinCombination.getCombinations())
                .hasSize(3);
    }

    @Test
    @Timeout(value = 10, unit = TimeUnit.MILLISECONDS)
    @DisplayName("Should throw exception when ZERO value for exchange is set.")
    public void shouldThrowException_whenZeroExchangeValueIsSet() {
        final var coinCombination = CoinCombination.of();

        Assertions.assertThrows(CoinCombination.CoinExchangeCombinationInvalid.class, () -> coinCombination.findCoinsCombinations(0));
    }

    @Test
    @Timeout(value = 10, unit = TimeUnit.MILLISECONDS)
    @DisplayName("Should throw exception when ZERO value for exchange is set.")
    public void shouldThrowException_whenLessThenZeroExchangeValueIsSet() {
        final var coinCombination = CoinCombination.of();

        Assertions.assertThrows(CoinCombination.CoinExchangeCombinationInvalid.class, () -> coinCombination.findCoinsCombinations(-1));
    }

    @Test
    @Timeout(value = 10, unit = TimeUnit.MILLISECONDS)
    @DisplayName("Should throw exception when BIGGER value for exchange is set.")
    public void shouldThrowException_whenBiggerExchangeValueIsSet() {
        final var coinCombination = CoinCombination.of();

        Assertions.assertThrows(CoinCombination.CoinExchangeCombinationInvalid.class, () -> coinCombination.findCoinsCombinations(850));
    }
}