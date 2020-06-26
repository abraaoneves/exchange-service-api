package br.com.abneves.exchange.domain.usecases;

@FunctionalInterface
public interface GiveDiscountUseCase {
    Integer getDiscount(final Integer totalOfPayments, final Integer productsValue);
}
