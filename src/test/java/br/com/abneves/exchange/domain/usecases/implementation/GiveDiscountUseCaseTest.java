package br.com.abneves.exchange.domain.usecases.implementation;

import br.com.abneves.exchange.domain.usecases.ChanceToGetDiscountUseCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
class GiveDiscountUseCaseTest {

    @Mock
    private ChanceToGetDiscountUseCase chanceToGetDiscountUseCase;

    @InjectMocks
    private GiveDiscountUseCaseImpl giveDiscountUseCase;

    @BeforeEach
    public void setUp() {
        when(chanceToGetDiscountUseCase.execute(any())).thenReturn(Boolean.TRUE);
    }

    @Test
    @DisplayName("Give discount for valid payment.")
    public void shouldBeSetDiscount() {
        assertThat(giveDiscountUseCase.getDiscount(3, 10)).isEqualTo(1);
        assertThat(giveDiscountUseCase.getDiscount(6, 10)).isEqualTo(1);
        assertThat(giveDiscountUseCase.getDiscount(90, 10)).isEqualTo(1);
    }

    @Test
    @DisplayName("Give zero of discount for invalid total of payments from Payer.")
    public void shouldBeReturnZeroOfDiscount() {
        assertThat(giveDiscountUseCase.getDiscount(2, 10)).isEqualTo(0);
        assertThat(giveDiscountUseCase.getDiscount(52, 10)).isEqualTo(0);
        assertThat(giveDiscountUseCase.getDiscount(91, 10)).isEqualTo(0);
    }

    @Test
    @DisplayName("Give zero of discount when gets bad chances.")
    public void shouldBeReturnZeroOfDiscount_whenChancesToGetDiscountIsInvalid() {
        when(chanceToGetDiscountUseCase.execute(any())).thenReturn(Boolean.FALSE);

        assertThat(giveDiscountUseCase.getDiscount(3, 10)).isEqualTo(0);
        assertThat(giveDiscountUseCase.getDiscount(2, 10)).isEqualTo(0);
        assertThat(giveDiscountUseCase.getDiscount(30, 10)).isEqualTo(0);
        assertThat(giveDiscountUseCase.getDiscount(92, 10)).isEqualTo(0);
    }
}