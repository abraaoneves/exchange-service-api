package br.com.abneves.exchange.controllers.vos.responses;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Value;

@Value
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class CoinResponse implements Comparable<CoinResponse> {

    Integer coinQuantity;
    String combination;

    public static CoinResponse of(final Integer coinQuantity, final String combination) {
        return new CoinResponse(coinQuantity, combination);
    }

    @Override
    public int compareTo(CoinResponse o) {
        if (o.getCoinQuantity() == null || this.getCoinQuantity() == null) {
            return 0;
        }

        return this.getCoinQuantity().compareTo(o.getCoinQuantity());
    }
}
