package br.com.abneves.exchange.controllers.vos.responses;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class CoinResponseTest {

    @Test
    @DisplayName("Should be return coin response.")
    public void shouldBeGenerateCoinResponse() {
        final var response = CoinResponse.of(2, "2, 3");

        assertThat(response.getCoinQuantity())
                .isEqualTo(2);

        assertThat(response.getCombination())
                .isEqualTo("2, 3");
    }

    @Test
    @DisplayName("Compare with another Coin response.")
    public void shouldBeCompareBetweenCoinResponses() {
        final var response1 = CoinResponse.of(2, "2, 3");
        final var response2 = CoinResponse.of(3, "2, 3, 5");
        final var response3 = CoinResponse.of(null, "2, 3, 5");

        assertThat(response1.compareTo(response2)).isEqualTo(-1);
        assertThat(response2.compareTo(response1)).isEqualTo(1);
        assertThat(response1.compareTo(response1)).isEqualTo(0);
        assertThat(response3.compareTo(response3)).isEqualTo(0);
    }
}
