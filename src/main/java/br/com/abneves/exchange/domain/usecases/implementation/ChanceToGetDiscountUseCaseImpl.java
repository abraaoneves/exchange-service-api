package br.com.abneves.exchange.domain.usecases.implementation;

import br.com.abneves.exchange.configuration.annotations.UseCase;
import br.com.abneves.exchange.domain.usecases.ChanceToGetDiscountUseCase;
import lombok.AllArgsConstructor;

import java.util.Random;

@UseCase
@AllArgsConstructor
public class ChanceToGetDiscountUseCaseImpl implements ChanceToGetDiscountUseCase {

    @Override
    public boolean execute(Integer totalOfPayments) {
        return ((new Random().nextInt(100)) > 17) ? Boolean.FALSE : Boolean.TRUE;
    }
}
