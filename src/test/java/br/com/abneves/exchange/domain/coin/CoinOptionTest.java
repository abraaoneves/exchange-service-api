package br.com.abneves.exchange.domain.coin;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class CoinOptionTest {

    @Test
    @DisplayName("Return list of one option coin and value exchange one.")
    public void generateCoinOption_whenValidValueOfExchangeWasSet_thenReturnAlternativesOfCoins() {
        final var option = CoinOption.of(1);

        assertThat(option.getExchange())
                .isEqualTo(1);

        assertThat(option.getOptions())
                .isNotEmpty()
                .hasSize(1);
    }

    @Test
    @DisplayName("Throw exception when zero value was informed.")
    public void throwInvalidValue_whenZeroValueOfExchangeWasSet() {
        assertThrows(CoinCombination.CoinExchangeCombinationInvalid.class, () -> CoinOption.of(0));
    }

    @Test
    @DisplayName("Throw exception when negative value was informed.")
    public void throwInvalidValue_whenLessThenZeroValueOfExchangeWasSet() {
        assertThrows(CoinCombination.CoinExchangeCombinationInvalid.class, () -> CoinOption.of(-1));
    }

    @Test
    @DisplayName("Throw exception when bigger value was informed.")
    public void throwInvalidValue_whenBiggerValueOfExchangeWasSet() {
        assertThrows(CoinCombination.CoinExchangeCombinationInvalid.class, () -> CoinOption.of(1500));
    }
}