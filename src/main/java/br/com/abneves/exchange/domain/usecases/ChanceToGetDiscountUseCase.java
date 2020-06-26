package br.com.abneves.exchange.domain.usecases;

@FunctionalInterface
public interface ChanceToGetDiscountUseCase {
    boolean execute(final Integer totalOfPayments);
}
