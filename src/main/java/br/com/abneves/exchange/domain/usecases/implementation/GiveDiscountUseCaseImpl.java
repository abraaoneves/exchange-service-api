package br.com.abneves.exchange.domain.usecases.implementation;

import br.com.abneves.exchange.configuration.annotations.UseCase;
import br.com.abneves.exchange.domain.usecases.ChanceToGetDiscountUseCase;
import br.com.abneves.exchange.domain.usecases.GiveDiscountUseCase;
import lombok.AllArgsConstructor;

@UseCase
@AllArgsConstructor
public class GiveDiscountUseCaseImpl implements GiveDiscountUseCase {

    private static final Integer ZER0 = 0;
    private static final Integer NUMBER_DELIMITER_RULE = 3;
    private static final Double TEN_PERCENT = 0.10;

    private final ChanceToGetDiscountUseCase chanceToGetDiscountUseCase;

    @Override
    public Integer getDiscount(final Integer totalOfPayments, final Integer productsValue) {
        if (this.payerHasValidPaymentsQuantity(totalOfPayments)) {
            final double discount = productsValue * TEN_PERCENT;
            return (int) discount;
        }

        return ZER0;
    }

    private boolean payerHasValidPaymentsQuantity(final Integer totalOfPayments) {
        if (chanceToGetDiscountUseCase.execute(totalOfPayments)) {
            return ((totalOfPayments % NUMBER_DELIMITER_RULE) != ZER0) ? Boolean.FALSE : Boolean.TRUE;
        }

        return Boolean.FALSE;
    }
}